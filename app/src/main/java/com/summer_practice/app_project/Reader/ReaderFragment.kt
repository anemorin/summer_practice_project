package com.summer_practice.app_project.Reader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.AppApi.AppAPI
import com.summer_practice.app_project.Main.MainAdapter
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentReaderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReaderFragment : Fragment(R.layout.fragment_reader) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chapterId = arguments?.getString("chapterID")
        val binding = FragmentReaderBinding.bind(view)
        val client = ApiClient().client
        binding.rvReader.layoutManager = LinearLayoutManager(context)
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val adapter = ReaderAdapter(client.getChapterImage(chapterId.toString()))
                binding.rvReader.adapter = adapter
            }
        }
    }
}