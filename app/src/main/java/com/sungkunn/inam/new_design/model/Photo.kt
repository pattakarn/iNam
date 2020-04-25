package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Photo(var item_id: String? = "",
                 var user_id: String? = "",
                 var image_url: String? = "",
                 var status: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(item_id)
        parcel.writeString(user_id)
        parcel.writeString(image_url)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }


}