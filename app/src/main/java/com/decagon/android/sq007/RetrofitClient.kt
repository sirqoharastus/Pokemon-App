package com.decagon.android.sq007

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.Observable
import okhttp3.logging.HttpLoggingInterceptor



object RetrofitClient {
    private const val BASE_URL: String = "https://darot-image-upload-service.herokuapp.com/api/v1/"

    fun getImage(): RetrofitInterface{
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
    }
}