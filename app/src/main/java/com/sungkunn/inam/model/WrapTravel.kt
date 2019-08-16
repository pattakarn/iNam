package com.sungkunn.inam.model

import android.os.Parcel
import android.os.Parcelable


class WrapTravel(var key: String,
                 var data: Travel): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapTravel> {
        override fun createFromParcel(parcel: Parcel): WrapTravel {
            return WrapTravel(parcel)
        }

        override fun newArray(size: Int): Array<WrapTravel?> {
            return arrayOfNulls(size)
        }
    }

}