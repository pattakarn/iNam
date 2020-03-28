package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class OrderDao(var id: String,
               var data: Order): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Order::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDao> {
        override fun createFromParcel(parcel: Parcel): OrderDao {
            return OrderDao(parcel)
        }

        override fun newArray(size: Int): Array<OrderDao?> {
            return arrayOfNulls(size)
        }
    }


}