package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Comment(var item_id: String? = "",
                   var user_id: String? = "",
                   var comment: String? = "",
                   var rating: String? = "") : Parcelable {

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
        parcel.writeString(comment)
        parcel.writeString(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }


}