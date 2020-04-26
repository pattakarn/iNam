package com.sungkunn.inam.old_ver.model

import android.os.Parcel
import android.os.Parcelable


class WrapMarket(var key: String,
                 var data: Market): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapMarket> {
        override fun createFromParcel(parcel: Parcel): WrapMarket {
            return WrapMarket(parcel)
        }

        override fun newArray(size: Int): Array<WrapMarket?> {
            return arrayOfNulls(size)
        }
    }

}