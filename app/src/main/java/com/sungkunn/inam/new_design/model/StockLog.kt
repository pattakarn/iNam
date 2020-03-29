package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class StockLog(var item_id: String? = "",
                    var user_id: String? = "",
                    var action: String? = "",
                    var quantity: String? = "",
                    var created_datetime: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(item_id)
        parcel.writeString(user_id)
        parcel.writeString(action)
        parcel.writeString(quantity)
        parcel.writeString(created_datetime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockLog> {
        override fun createFromParcel(parcel: Parcel): StockLog {
            return StockLog(parcel)
        }

        override fun newArray(size: Int): Array<StockLog?> {
            return arrayOfNulls(size)
        }
    }


}