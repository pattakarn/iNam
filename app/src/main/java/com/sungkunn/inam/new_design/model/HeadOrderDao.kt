package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class HeadOrderDao(var id: String,
                   var data: HeadOrder): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(HeadOrder::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HeadOrderDao> {
        override fun createFromParcel(parcel: Parcel): HeadOrderDao {
            return HeadOrderDao(parcel)
        }

        override fun newArray(size: Int): Array<HeadOrderDao?> {
            return arrayOfNulls(size)
        }
    }


}