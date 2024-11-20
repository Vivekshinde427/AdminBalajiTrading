package com.example.AdminBalajiTrading

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.AdminBalajiTrading.databinding.ActivitySignUpBinding
import com.example.AdminBalajiTrading.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import javax.security.auth.callback.PasswordCallback

class SignUpActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth



    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username:String
    private lateinit var nameofStore:String
    private lateinit var detabase:DatabaseReference
    private  val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)



        auth= Firebase.auth
        detabase=Firebase.database.reference


        binding.createAccountButton.setOnClickListener {

            username=binding.name.text.toString().trim()
            nameofStore=binding.organization.toString().trim()
            email=binding.editTextTextEmailAddress.text.toString().trim()
            password=binding.editTextTextPassword.text.toString().trim()

            if (username.isBlank()||nameofStore.isBlank()||email.isBlank()||password.isBlank()){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            }else{

                createAccount(email,password)
            }





        }
        binding.alreadyHaveAccountButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val locationlist= arrayOf("Nashik","Ahmadnagar","Yeola","Kopargaon")
        val adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,locationlist)
        val autoCompleteTextView=binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)

        }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                Toast.makeText(this,"Account created",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
            else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount:Failure",task.exception)
            }
        }
    }
//save data in to detabase
    private fun saveUserData() {
        username=binding.name.text.toString().trim()
        nameofStore=binding.organization.toString().trim()
        email=binding.editTextTextEmailAddress.text.toString().trim()
        password=binding.editTextTextPassword.text.toString().trim()
        val user= UserModel(username,nameofStore,email,password)
        val userId: String =FirebaseAuth.getInstance().currentUser!!.uid
        detabase.child("user").child(userId).setValue(user)
    }
}
