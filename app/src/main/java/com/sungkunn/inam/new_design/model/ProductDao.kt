package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class ProductDao(var id: String,
                 var data: Product): Parcelable {

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

    companion object CREATOR : Parcelable.Creator<ProductDao> {
        override fun createFromParcel(parcel: Parcel): ProductDao {
            return ProductDao(parcel)
        }

        override fun newArray(size: Int): Array<ProductDao?> {
            return arrayOfNulls(size)
        }
    }


}