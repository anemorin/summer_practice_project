package com.summer_practice.app_project.Main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.AppApi.AppAPI
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding : FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
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
        binding.rvMain.layoutManager = LinearLayoutManager(context)
        val listCollections = mutableListOf<ApiMultiItem>()

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                listCollections.add(service.getListLatestUpdate())
                listCollections.add(service.getList2023())
                listCollections.add(service.getListNaruto())
                listCollections.add(service.getListByOdaEiichiro())
                listCollections.add(service.getListPokemon())
                listCollections.add(service.getListNinja())
                listCollections.add(service.getListSamurai())
                val adapter = MainAdapter(listCollections)
                binding.rvMain.adapter = adapter
            }
        }
    }
}