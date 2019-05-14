package com.sungkunn.inam.db

import android.os.Parcel
import android.os.Parcelable


class WrapHotel(var key: String,
                var data: Hotel): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("data")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WrapHotel> {
        override fun createFromParcel(parcel: Parcel): WrapHotel {
            return WrapHotel(parcel)
        }

        override fun newArray(size: Int): Array<WrapHotel?> {
            return arrayOfNulls(size)
        }
    }

}