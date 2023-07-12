package com.summer_practice.app_project.ComicsPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentComicsPageBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicsPageFragment : Fragment(R.layout.fragment_comics_page) {
    private lateinit var binding : FragmentComicsPageBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComicsPageBinding.bind(view)
        val mangaId = arguments?.getString("ID")
        val client = ApiClient().client
        val sharedPreferences = activity?.getSharedPreferences("APP", Context.MODE_PRIVATE);
        val sessionToken = sharedPreferences?.getString("token", null)
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val mangaItem = client.getMangaById(mangaId.toString())
                val chapterItem = client.getMangaChapters(mangaId.toString())

                if (sessionToken != null) {
                    binding.ibBookmark.visibility = View.VISIBLE
                    val result =
                        client.checkUserFollowedManga(mangaItem.data.id, sessionToken.toString())
                    if (result.code() == 200)
                        binding.ibBookmark.setImageResource(R.drawable.v_bookmark_added)
                }

                binding.tvEnName.text = mangaItem.data.attributes.title.en
                binding.tvDescription.text = mangaItem.data.attributes.description.en
                binding.tvStatus.text = mangaItem.data.attributes.status
                binding.tvCountChapters.text = chapterItem.data.size.toString()

                if (mangaItem.data.attributes.year.toString() == "0")
                    binding.tvYear.text = R.string.no_data.toString()
                else
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

                val adapter = ChapterListAdapter(chapterItem) { item ->
                    NavHostFragment.findNavController(view.findFragment())
                        .navigate(R.id.action_comicsPageFragment_to_readerFragment,
                            makeBundle(item))}

                binding.rvChaptersList.layoutManager = LinearLayoutManager(context)
                binding.rvChaptersList.adapter = adapter

                binding.run {
                    ibBackArroy.setOnClickListener {
                        val fm : FragmentManager = parentFragmentManager
                        fm.popBackStack()
                    }

                    ibBookmark.setOnClickListener {
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                val client = ApiClient().client
                                val result = client.checkUserFollowedManga(mangaId.toString(),
                                    sessionToken.toString()
                                )
                                if (result.code() == 200) {
                                    client.deleteMangaFromWishList(mangaId.toString(),
                                        sessionToken.toString())
                                    binding.ibBookmark.setImageResource(R.drawable.v_bookmark_empty)
                                }
                                else {
                                    client.addMangaToWishList(mangaId.toString(),
                                        sessionToken.toString())
                                    binding.ibBookmark.setImageResource(R.drawable.v_bookmark_added)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private fun makeBundle(id : String): Bundle {
            var bundle = Bundle()
            bundle.putString("chapterID", id)
            return bundle
        }
    }
}