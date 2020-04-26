package com.sungkunn.inam.old_ver.model

import android.os.Parcel
import android.os.Parcelable


class WrapZone(var key: String,
               var data: Zone): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapZone> {
        override fun createFromParcel(parcel: Parcel): WrapZone {
            return WrapZone(parcel)
        }

        override fun newArray(size: Int): Array<WrapZone?> {
            return arrayOfNulls(size)
        }
    }

}