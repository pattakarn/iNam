package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class PlacePackDao(var id: String,
                   var data: Place,
                   var photo: ArrayList<PhotoDao>): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Place::class.java.classLoader),
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

    companion object CREATOR : Parcelable.Creator<PlacePackDao> {
        override fun createFromParcel(parcel: Parcel): PlacePackDao {
            return PlacePackDao(parcel)
        }

        override fun newArray(size: Int): Array<PlacePackDao?> {
            return arrayOfNulls(size)
        }
    }


}