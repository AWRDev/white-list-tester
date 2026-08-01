package com.awrdev.white_list_tester.api


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface SimpleApi {
    @GET("pogoda")
    suspend fun getYandex(): Response<ResponseBody>

    @GET("pogoda")
    suspend fun getOzon(): Response<ResponseBody>

    @GET("best")
    suspend fun getPikabu(): Response<ResponseBody>

}