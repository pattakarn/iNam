package com.sungkunn.inam.new_design.model

import android.os.Parcel
import android.os.Parcelable

data class Product(var name: String? = "",
                   var image_url: String? = "",
                   var type: String? = "",
                   var detail: String? = "",
                   var price: String? = "",
                   var place_id: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(image_url)
        parcel.writeString(type)
        parcel.writeString(detail)
        parcel.writeString(price)
        parcel.writeString(place_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}