package com.tempo.news.data.model

import android.os.Parcel
import android.os.Parcelable

data class ArticleDM(
    val source:SourceDM?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(SourceDM::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleDM> {
        override fun createFromParcel(parcel: Parcel): ArticleDM {
            return ArticleDM(parcel)
        }

        override fun newArray(size: Int): Array<ArticleDM?> {
            return arrayOfNulls(size)
        }
    }

}