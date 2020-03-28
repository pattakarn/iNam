package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class CommunityDao(var id: String,
                   var data: Community): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Community::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommunityDao> {
        override fun createFromParcel(parcel: Parcel): CommunityDao {
            return CommunityDao(parcel)
        }

        override fun newArray(size: Int): Array<CommunityDao?> {
            return arrayOfNulls(size)
        }
    }


}