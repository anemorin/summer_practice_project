package com.summer_practice.app_project.AppApi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val client: AppAPI
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retroFit = Retrofit.Builder()
                .baseUrl("https://api.mangadex.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return  retroFit.create(AppAPI::class.java)
        }
}