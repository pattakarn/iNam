package com.sungkunn.inam.db

import android.os.Parcel
import android.os.Parcelable


class WrapShop(var key: String,
               var data: Shop): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapShop> {
        override fun createFromParcel(parcel: Parcel): WrapShop {
            return WrapShop(parcel)
        }

        override fun newArray(size: Int): Array<WrapShop?> {
            return arrayOfNulls(size)
        }
    }

}