package com.sungkunn.inam.old_ver.model

import android.os.Parcel
import android.os.Parcelable


class WrapNetwork(var key: String,
                  var data: Network): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapNetwork> {
        override fun createFromParcel(parcel: Parcel): WrapNetwork {
            return WrapNetwork(parcel)
        }

        override fun newArray(size: Int): Array<WrapNetwork?> {
            return arrayOfNulls(size)
        }
    }

}