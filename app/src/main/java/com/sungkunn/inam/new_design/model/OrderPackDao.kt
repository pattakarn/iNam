package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class OrderPackDao(var id: String,
                   var data: Order,
                   var product: ProductPackDao): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Order::class.java.classLoader),
        parcel.readParcelable(ProductPackDao::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
        parcel.writeParcelable(product, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderPackDao> {
        override fun createFromParcel(parcel: Parcel): OrderPackDao {
            return OrderPackDao(parcel)
        }

        override fun newArray(size: Int): Array<OrderPackDao?> {
            return arrayOfNulls(size)
        }
    }


}