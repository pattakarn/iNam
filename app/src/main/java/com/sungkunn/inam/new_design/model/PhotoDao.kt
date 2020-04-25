package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class PhotoDao(var id: String,
               var data: Photo): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Photo::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoDao> {
        override fun createFromParcel(parcel: Parcel): PhotoDao {
            return PhotoDao(parcel)
        }

        override fun newArray(size: Int): Array<PhotoDao?> {
            return arrayOfNulls(size)
        }
    }


}