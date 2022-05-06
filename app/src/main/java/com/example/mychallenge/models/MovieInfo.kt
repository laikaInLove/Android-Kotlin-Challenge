package com.example.mychallenge.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieInfo(
    @SerializedName("original_title")
    val title : String?,

    @SerializedName("release_date")
    val releaseDate : String?,

    @SerializedName("runtime")
    val runtime : String?,

    @SerializedName("genres")
    val genres : List<MovieGenre>,

    @SerializedName("original_language")
    val originalLanguage : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("popularity")
    val popularityMovie : Number?,

    @SerializedName("poster_path")
    val poster_path : String?,


    ) : Parcelable{
    constructor() : this("","","", mutableListOf(),"", "",1,"")
}

