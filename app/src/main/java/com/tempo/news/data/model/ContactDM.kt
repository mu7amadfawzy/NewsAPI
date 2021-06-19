package com.tempo.news.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * the contact data model
 * */
data class ContactDM(val contactName: String? = null, val phone: String? = null) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contactName)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactDM> {
        override fun createFromParcel(parcel: Parcel): ContactDM {
            return ContactDM(parcel)
        }

        override fun newArray(size: Int): Array<ContactDM?> {
            return arrayOfNulls(size)
        }
    }
}