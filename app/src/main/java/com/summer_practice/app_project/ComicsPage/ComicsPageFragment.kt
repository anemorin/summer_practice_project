package com.summer_practice.app_project.ComicsPage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.AppAPI
import com.summer_practice.app_project.Main.MainAdapter
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentComicsPageBinding
import com.summer_practice.app_project.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComicsPageFragment : Fragment(R.layout.fragment_comics_page) {
    private lateinit var binding : FragmentComicsPageBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComicsPageBinding.bind(view)
        val mangaId = arguments?.getString("ID")

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

        binding.rvChapters.layoutManager = LinearLayoutManager(context)
        val service = retroFit.create(AppAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val mangaItem = service.getMangaById(mangaId.toString())
                val chaptersItem = mangaId?.let { service.getMangaChapters(it) }

                val adapter = chaptersItem?.let { ChapterListAdapter(it) {it ->
                    NavHostFragment.findNavController(view.findFragment())
                        .navigate(R.id.action_comicsPageFragment_to_readerFragment,
                            MakeBundle(it)
                        )
                } }
                binding.rvChapters.adapter = adapter

                if ((mangaItem.data.attributes.title.en)?.isEmpty() == true)
                    for (i in mangaItem.data.attributes.altTitles){
                        if (i.en?.isEmpty() == false) {
                            binding.tvEnName.text = i.en
                            break
                        }
                    }
                else
                    binding.tvEnName.text = mangaItem.data.attributes.title.en

                binding.tvDescription.text = mangaItem.data.attributes.description.en
                binding.tvStatus.text = mangaItem.data.attributes.status
                binding.tvYear.text = mangaItem.data.attributes.year.toString()
                var link = ""
                for (i in mangaItem.data.relationships) {
                    if (i.type == "author")
                        binding.tvAuthorName.text = i.attributes?.name
                    if (i.type == "cover_art")
                        link = i.attributes?.fileName.toString()
                }
                val url = "https://uploads.mangadex.org/covers/${mangaItem.data.id}/$link"
                Glide.with(binding.root).load(url).into(binding.ivBookIcon)

            }
        }
    }

    companion object {
        private fun MakeBundle(id : String): Bundle {
            var bundle = Bundle()
            bundle.putString("ID", id)
            return bundle
        }
    }
}