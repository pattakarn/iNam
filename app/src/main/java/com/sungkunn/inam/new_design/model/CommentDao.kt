package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class CommentDao(var id: String,
                 var data: Comment): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Comment::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentDao> {
        override fun createFromParcel(parcel: Parcel): CommentDao {
            return CommentDao(parcel)
        }

        override fun newArray(size: Int): Array<CommentDao?> {
            return arrayOfNulls(size)
        }
    }


}