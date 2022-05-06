package com.example.mychallenge.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class SessionResponse(
    @SerializedName("success")
    val success : Boolean,

    @SerializedName("guest_session_id")
    val sessionId : String,

    @SerializedName("expires_at")
    val expiresAt : String


) : Parcelable{

    constructor() : this(false,"", "")
}