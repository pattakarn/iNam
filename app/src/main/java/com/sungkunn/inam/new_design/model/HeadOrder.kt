package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class HeadOrder(
    var user_id: String? = "",
    var status: String? = "",
    var total_quantity: String? = "",
    var total_price: String? = "",
    var created_datetime: String? = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user_id)
        parcel.writeString(status)
        parcel.writeString(total_quantity)
        parcel.writeString(total_price)
        parcel.writeString(created_datetime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeadOrder> {
        override fun createFromParcel(parcel: Parcel): HeadOrder {
            return HeadOrder(parcel)
        }

        override fun newArray(size: Int): Array<HeadOrder?> {
            return arrayOfNulls(size)
        }
    }


}