package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class PlaceTest(var name: String = "",
                     var type: String = "",
                     var detail: String = "",
                     var image: String = "",
                     var lat: String = "",
                     var lng: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeString(name)
        dest!!.writeString(type)
        dest!!.writeString(detail)
        dest!!.writeString(image)
        dest!!.writeString(lat)
        dest!!.writeString(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceTest> {
        override fun createFromParcel(parcel: Parcel): PlaceTest {
            return PlaceTest(parcel)
        }

        override fun newArray(size: Int): Array<PlaceTest?> {
            return arrayOfNulls(size)
        }
    }

}