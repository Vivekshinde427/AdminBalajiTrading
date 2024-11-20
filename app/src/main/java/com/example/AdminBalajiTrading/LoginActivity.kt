@file:Suppress("DEPRECATION")

package com.example.AdminBalajiTrading

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.AdminBalajiTrading.databinding.ActivityLoginBinding
import com.example.AdminBalajiTrading.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.net.PasswordAuthentication
import kotlin.math.log

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private  var username:String?=null
    private  var nameofStore:String?=null
    private lateinit var email: String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var googleSignInclient: GoogleSignInClient


    private  val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val googleSignInAccountOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_idd)).requestEmail().build()
        auth=Firebase.auth

        database=Firebase.database.reference

        googleSignInclient=GoogleSignIn.getClient(this,googleSignInAccountOptions)

        binding.Loginbutton.setOnClickListener {

            email = binding.editTextTextEmailAddress.text.toString().trim()
            password=binding.editTextTextPassword.text.toString().trim()

            if (email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please Fill All Details",Toast.LENGTH_SHORT).show()
            }else{
                createUserAccount(email,password)
            }




        }
        binding.Googlebutton.setOnClickListener{
            val signIntent: Intent =googleSignInclient.signInIntent


            launcher.launch(signIntent)
        }
        binding.dontHaveAccountButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if (task.isSuccessful){
                val user: FirebaseUser?=auth.currentUser
                Toast.makeText(this,"Login Succesfully",Toast.LENGTH_SHORT).show()
                upadteUi(user)

            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task->
                    if (task.isSuccessful){
                        val user: FirebaseUser?=auth.currentUser
                        Toast.makeText(this,"Create User and Login Succesfully",Toast.LENGTH_SHORT).show()
                        saveUserDate()
                        upadteUi(user)
                    }else{
                        Toast.makeText(this,"Authentification Failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account","createUserAccount:Authentification failed",task.exception)
                    }
                }
            }
        }

    }

    private fun saveUserDate() {
        email = binding.editTextTextEmailAddress.text.toString().trim()
        password=binding.editTextTextPassword.text.toString().trim()
        val user= UserModel(username,nameofStore,email,password)
        val userId: String? =FirebaseAuth.getInstance().currentUser?.uid
        userId.let {
            if (it != null) {
                database.child("user").child(it).setValue(user)
            }
        }
    }

    private fun upadteUi(user: FirebaseUser?) {

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode== Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account:GoogleSignInAccount=task.result

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential).addOnCompleteListener{authTask->
                    if (authTask.isSuccessful){
                        Toast.makeText(this,"Succesfully Sign in with Google",Toast.LENGTH_SHORT).show()
                        upadteUi(user = null)

                    }else{
                        Toast.makeText(this,"Google SignIn Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Google SignIn Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

}

