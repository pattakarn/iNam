package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class User(var firstname: String? = "",
                var lastname: String? = "",
                var nickname: String? = "",
                var phone: String? = "",
                var email: String? = "",
                var photoURL: String? = "",
                var role: String? = "" ) : Parcelable {

    constructor(parcel: Parcel) : this(
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
        parcel.writeString(firstname)
        parcel.writeString(lastname)
        parcel.writeString(nickname)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(photoURL)
        parcel.writeString(role)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}