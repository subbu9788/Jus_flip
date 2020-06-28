package com.be.positive.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class APIClient {

    companion object {

        var retrofit: Retrofit? = null
        //val URL = "http://192.168.10.117/court/court/public/"
        val URL = "http://18.218.35.158/"
        val BASE_URL = URL + "api/court/"

        fun getClient(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().connectTimeout(170, TimeUnit.SECONDS)
                .readTimeout(170, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit as Retrofit
        }
    }
}