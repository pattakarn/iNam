package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Order(
    var headorder_id: String? = "",
    var user_id: String? = "",
    var product_id: String? = "",
    var place_id: String? = "",
    var quantity: String? = "",
    var total_price: String? = "",
    var status: String? = "",
    var updated_datetime: String? = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(headorder_id)
        parcel.writeString(user_id)
        parcel.writeString(product_id)
        parcel.writeString(place_id)
        parcel.writeString(quantity)
        parcel.writeString(total_price)
        parcel.writeString(status)
        parcel.writeString(updated_datetime)
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