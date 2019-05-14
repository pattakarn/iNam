package com.sungkunn.inam.db

import android.os.Parcel
import android.os.Parcelable


class WrapService(var key: String,
                  var data: Service): Parcelable {
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

    companion object CREATOR : Parcelable.Creator<WrapService> {
        override fun createFromParcel(parcel: Parcel): WrapService {
            return WrapService(parcel)
        }

        override fun newArray(size: Int): Array<WrapService?> {
            return arrayOfNulls(size)
        }
    }

}