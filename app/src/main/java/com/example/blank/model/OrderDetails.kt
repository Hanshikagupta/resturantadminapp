package com.example.blank.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails():Serializable {
    var userUid: String?=null
    var userName: String?=null
    var foodName: MutableList<String>?=null
    var foodImages: MutableList<String>?=null
    var foodPrices: MutableList<String>?=null
    var foodQuantities: MutableList<Int>?=null
    var tableno: String?=null
    var totalPrice: String?=null
    var phoneNumber: String?=null
    var orderAccepted: Boolean=false
    var paymentReceived: Boolean=false
    var itemPushKey:String?=null
    var currentTime: Long =0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        tableno = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

     fun writeToParcel(parcel: Parcel, flags: Int) {

    }

     fun describeContents(): Int {
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