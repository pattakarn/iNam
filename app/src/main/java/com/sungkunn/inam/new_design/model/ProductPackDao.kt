package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable


class ProductPackDao(var id: String,
                     var data: Product,
                     var photo: ArrayList<PhotoDao> ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Product::class.java.classLoader),
        arrayListOf<PhotoDao>().apply {
            parcel.readList(
                this, PhotoDao::class.java.classLoader
            )
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(data, flags)
        arrayListOf<PhotoDao>().apply {
            parcel.writeList(photo)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductPackDao> {
        override fun createFromParcel(parcel: Parcel): ProductPackDao {
            return ProductPackDao(parcel)
        }

        override fun newArray(size: Int): Array<ProductPackDao?> {
            return arrayOfNulls(size)
        }
    }


}