package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class ProductOrderDao(var id: String,
                      var data: ProductOrder): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Product::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductOrderDao> {
        override fun createFromParcel(parcel: Parcel): ProductOrderDao {
            return ProductOrderDao(parcel)
        }

        override fun newArray(size: Int): Array<ProductOrderDao?> {
            return arrayOfNulls(size)
        }
    }


}