package com.example.mychallenge.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieGenre(@SerializedName("id")
                      val id : Int,

                      @SerializedName("name")
                      val name : String,) :
    Parcelable{
    constructor() : this(0,"") {

}
    }