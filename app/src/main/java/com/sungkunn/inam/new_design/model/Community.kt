package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Community(var community_name: String? = "",
                     var image_url: String? = "",
                     var type: String? = "",
                     var owner: String? = "",
                     var phone: String? = "",
                     var line: String? = "",
                     var facebook: String? = "",
                     var email: String? = "",
                     var address: String? = "",
                     var latitude: String? = "",
                     var longitude: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(community_name)
        parcel.writeString(image_url)
        parcel.writeString(type)
        parcel.writeString(owner)
        parcel.writeString(phone)
        parcel.writeString(line)
        parcel.writeString(facebook)
        parcel.writeString(email)
        parcel.writeString(address)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Community> {
        override fun createFromParcel(parcel: Parcel): Community {
            return Community(parcel)
        }

        override fun newArray(size: Int): Array<Community?> {
            return arrayOfNulls(size)
        }
    }


}