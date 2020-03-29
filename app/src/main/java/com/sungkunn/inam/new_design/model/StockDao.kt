package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class StockDao(var id: String,
               var data: Stock): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Stock::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockDao> {
        override fun createFromParcel(parcel: Parcel): StockDao {
            return StockDao(parcel)
        }

        override fun newArray(size: Int): Array<StockDao?> {
            return arrayOfNulls(size)
        }
    }


}