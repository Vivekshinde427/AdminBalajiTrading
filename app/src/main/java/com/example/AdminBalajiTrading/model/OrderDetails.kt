package com.example.AdminBalajiTrading.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class OrderDetails() :Parcelable {

    var userUid:String?=null
    var userName:String?=null
    var foodNames:MutableList<String>?=null
    var foodImages:MutableList<String>?=null
    var foodPrices:MutableList<String>?=null
    var foodQuantities:MutableList<String>?=null
    var address:String?=null
    var totalprice:String?=null
    var phoneNumber:String?=null
    var orderAccepted:Boolean?=false
    var paymentRecevied:Boolean?=false
    var itemPushKey:String?=null
    var currentTime: Int =0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalprice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        paymentRecevied = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        itemPushKey = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalprice)
        parcel.writeString(phoneNumber)
        parcel.writeValue(orderAccepted)
        parcel.writeValue(paymentRecevied)
        parcel.writeString(itemPushKey)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }


}