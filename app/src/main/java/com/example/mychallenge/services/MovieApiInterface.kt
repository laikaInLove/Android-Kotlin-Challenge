package com.example.mychallenge.services

import com.example.mychallenge.models.*
import retrofit2.Call
import retrofit2.http.*

interface MovieApiInterface{

    @GET("movie/popular?api_key=247732dd25438182f26b2f26b1dba46e")
    fun getMovieList(@Query("page") page: Int) : Call<MovieResponse>

    @GET("movie/{id}?api_key=247732dd25438182f26b2f26b1dba46e")
    fun getMovieInfo(@Path("id") id : String) : Call<MovieInfo>

    @GET("authentication/guest_session/new?api_key=247732dd25438182f26b2f26b1dba46e")
    fun getSession() : Call<SessionResponse>

    @POST("movie/{id}/rating?api_key=247732dd25438182f26b2f26b1dba46e")
    fun rateMovie(@Path("id") movieId: String, @Query("guest_session_id") session : String,
                  @Body data: RatingMovie): Call<ResponseRating>

}