package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Order(
    var user_id: String? = "",
    var status: String? = "",
    var created_datetime: String? = "",
    var updated_datetime: String? = "",
    var product_order: List<String> = ArrayList()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user_id)
        parcel.writeString(status)
        parcel.writeString(created_datetime)
        parcel.writeString(updated_datetime)
        parcel.writeStringList(product_order)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }


}