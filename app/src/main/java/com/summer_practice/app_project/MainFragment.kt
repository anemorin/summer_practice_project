package com.summer_practice.app_project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.summer_practice.app_project.AppApi.AppAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val service = retroFit.create(AppAPI::class.java)
    }
}