package com.example.deltatask2
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface apiinterface{
    @GET("tips")
    fun getData(): Call<tips>

   @POST("characters")
   fun getplayer(@Body requestModel: p): Call<players>

}