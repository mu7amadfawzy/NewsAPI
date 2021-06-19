package com.tempo.news.data.model

import android.os.Parcel
import android.os.Parcelable

data class SourceDM(
    val id: String?,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SourceDM> {
        override fun createFromParcel(parcel: Parcel): SourceDM {
            return SourceDM(parcel)
        }

        override fun newArray(size: Int): Array<SourceDM?> {
            return arrayOfNulls(size)
        }
    }

}