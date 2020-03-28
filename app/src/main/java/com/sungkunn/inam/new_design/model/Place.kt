package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Place(var name: String? = "",
                 var type: String? = "",
                 var owner: String? = "",
                 var phone: String? = "",
                 var line: String? = "",
                 var facebook: String? = "",
                 var email: String? = "",
                 var address: String? = "",
                 var latitude: String? = "",
                 var longitude: String? = "",
                 var monday_open: String? = "",
                 var monday_close: String? = "",
                 var tuesday_open: String? = "",
                 var tuesday_close: String? = "",
                 var wednesday_open: String? = "",
                 var wednesday_close: String? = "",
                 var thursday_open: String? = "",
                 var thursday_close: String? = "",
                 var friday_open: String? = "",
                 var friday_close: String? = "",
                 var saturday_open: String? = "",
                 var saturday_close: String? = "",
                 var sunday_open: String? = "",
                 var sunday_close: String? = "",
                 var community_id: String? = "") : Parcelable {

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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(owner)
        parcel.writeString(phone)
        parcel.writeString(line)
        parcel.writeString(facebook)
        parcel.writeString(email)
        parcel.writeString(address)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(monday_open)
        parcel.writeString(monday_close)
        parcel.writeString(tuesday_open)
        parcel.writeString(tuesday_close)
        parcel.writeString(wednesday_open)
        parcel.writeString(wednesday_close)
        parcel.writeString(thursday_open)
        parcel.writeString(thursday_close)
        parcel.writeString(friday_open)
        parcel.writeString(friday_close)
        parcel.writeString(saturday_open)
        parcel.writeString(saturday_close)
        parcel.writeString(sunday_open)
        parcel.writeString(sunday_close)
        parcel.writeString(community_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel): Place {
            return Place(parcel)
        }

        override fun newArray(size: Int): Array<Place?> {
            return arrayOfNulls(size)
        }
    }


}