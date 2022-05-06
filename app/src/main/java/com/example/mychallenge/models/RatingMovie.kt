package com.example.mychallenge.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RatingMovie (
    @SerializedName("value")
    var value : Number?

) : Parcelable{
    constructor() : this(0.0)
}