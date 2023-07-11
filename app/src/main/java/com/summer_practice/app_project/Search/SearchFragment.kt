package com.summer_practice.app_project.Search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment

import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer_practice.app_project.AppApi.AppAPI
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentSearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.run {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        makeApiWork(query)
                    }
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    if (text != null) {
                        makeApiWork(text)
                    }
                    return true
                }
            })

        }
    }

    private fun makeApiWork(text : String) {
        binding = view?.let { FragmentSearchBinding.bind(it) }!!
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

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val item = service.searchManga(text)
                binding.rvSearch.layoutManager = LinearLayoutManager(context)
                val adapter = SearchAdapter(item) {
                    NavHostFragment.findNavController(requireView().findFragment())
                        .navigate(R.id.action_searchFragment_to_comicsPageFragment,
                            makeBundle(it)
                        )
                }
                binding.rvSearch.adapter = adapter
            }
        }
    }

    companion object {
        private fun makeBundle(id : String): Bundle {
            var bundle = Bundle()
            bundle.putString("ID", id)
            return bundle
        }
    }
}