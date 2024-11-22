package com.example.AdminBalajiTrading.adpater

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.AdminBalajiTrading.PendingOrderActivity
import com.example.AdminBalajiTrading.databinding.PendingOrdersItemsBinding

class PendingOrderAdapter(
    private val context: PendingOrderActivity,
    private val customerNames:MutableList<String>,
    private val quantity: MutableList<String>,
    private val productImage: MutableList<String>,
   // private val itemClicked: OnItemClicked,
): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding=PendingOrdersItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }




    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int =customerNames.size

    inner class PendingOrderViewHolder(private val binding:PendingOrdersItemsBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted=false
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            binding.apply {
                customerName.text=customerNames[position]
                pendingOrderQuantity.text=quantity[position]
                var uriString = productImage[position]
                var uri= Uri.parse(uriString)
                Glide.with(context).load(uri).into(orderProductImage)
                orderAcceptButton.apply {
                    if(!isAccepted){
                        text="Accept"

                    }else{
                        text="Dispatch"
                    }
                    setOnClickListener {

                        if(!isAccepted){
                            text="Dispatch"

                            isAccepted=true
                            showToast("Order is Accepted")



                        }else{

                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is dispatched")

                        }

                    }
                }


            }
        }
        private fun showToast(message: String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }
}