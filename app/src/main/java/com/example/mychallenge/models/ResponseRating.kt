package com.example.mychallenge.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ResponseRating (
    @SerializedName("status_code")
    val status_code : Int?,

    @SerializedName("status_message")
val status_message : String?

) : Parcelable{
    constructor() : this(0,"")
}