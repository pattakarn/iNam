package com.sungkunn.inam.model

import android.os.Parcel
import android.os.Parcelable


class WrapProduct(var key: String,
                  var data: Product): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapProduct> {
        override fun createFromParcel(parcel: Parcel): WrapProduct {
            return WrapProduct(parcel)
        }

        override fun newArray(size: Int): Array<WrapProduct?> {
            return arrayOfNulls(size)
        }
    }

}