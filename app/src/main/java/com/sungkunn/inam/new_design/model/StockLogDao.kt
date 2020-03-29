package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class StockLogDao(var id: String,
                  var data: StockLog): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(StockLog::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockLogDao> {
        override fun createFromParcel(parcel: Parcel): StockLogDao {
            return StockLogDao(parcel)
        }

        override fun newArray(size: Int): Array<StockLogDao?> {
            return arrayOfNulls(size)
        }
    }


}