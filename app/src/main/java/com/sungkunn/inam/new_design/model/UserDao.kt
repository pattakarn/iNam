package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class UserDao(var id: String,
              var data: User): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDao> {
        override fun createFromParcel(parcel: Parcel): UserDao {
            return UserDao(parcel)
        }

        override fun newArray(size: Int): Array<UserDao?> {
            return arrayOfNulls(size)
        }
    }


}