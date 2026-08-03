package com.awrdev.white_list_tester.api

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RetrofitInstance {
//    val retrofit by lazy {
        val logging = HttpLoggingInterceptor()

        val client = OkHttpClient.Builder()
            .writeTimeout(2500, TimeUnit.MILLISECONDS)
            .readTimeout(2500, TimeUnit.MILLISECONDS)
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(logging)
            .build()

//    }
    var call: Call? = null

    fun prepareCall(url: String){
        val request = Request.Builder()
            .url(url)
            .build()
        call = client.newCall(request)
    }
}