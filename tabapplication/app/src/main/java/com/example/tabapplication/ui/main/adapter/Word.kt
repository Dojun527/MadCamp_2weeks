package com.example.tabapplication.ui.main.adapter

import android.os.Parcel
import android.os.Parcelable

data class Word(
    var vocabulary: String?,
    var meaning: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(vocabulary)
        writeString(meaning)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Word> = object : Parcelable.Creator<Word> {
            override fun createFromParcel(source: Parcel): Word = Word(source)
            override fun newArray(size: Int): Array<Word?> = arrayOfNulls(size)
        }
    }
}