package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class CommunityPackDao(var id: String,
                       var data: Community,
                       var photo: ArrayList<PhotoDao>): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Community::class.java.classLoader),
        arrayListOf<PhotoDao>().apply {
            parcel.readList(this, PhotoDao::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
        arrayListOf<PhotoDao>().apply {
            parcel.writeList(photo)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommunityPackDao> {
        override fun createFromParcel(parcel: Parcel): CommunityPackDao {
            return CommunityPackDao(parcel)
        }

        override fun newArray(size: Int): Array<CommunityPackDao?> {
            return arrayOfNulls(size)
        }
    }


}