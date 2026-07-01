package com.awrdev.white_list_tester.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .writeTimeout(2500, TimeUnit.MILLISECONDS)
            .readTimeout(2500, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://yandex.ru/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}