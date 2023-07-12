package com.summer_practice.app_project.Collections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.AppApi.MangaItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ItemBookForFragmentCollectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectionAdapter(apiItem : ApiMultiItem, sessionToken : String, private val onItemClick : (String) -> Unit) :
    RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private val mangaItems = apiItem.data
    private val sessionToken = sessionToken
    class CollectionViewHolder(view: View, private val onItemClick : (String) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val binding = ItemBookForFragmentCollectionBinding.bind(view)
        fun onBind(item: MangaItem, sessionToken: String) {
            binding.run {
                if (sessionToken == "") {
                    binding.bookmarkButton.visibility = View.INVISIBLE
                    var imageFile = ""
                    for (i in item.relationships) {
                        if (i.type == "cover_art")
                            imageFile = i.attributes?.fileName.toString()
                    }
                    val url = "https://uploads.mangadex.org/covers/${item.id}/$imageFile"
                    tvTitle.text = item.attributes.title.en
                    Glide.with(binding.root).load(url).into(ivBookCover)
                }
                else {
                    GlobalScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            val client = ApiClient().client
                            val result = client.checkUserFollowedManga(item.id, sessionToken)
                            if (result.code() == 200)
                                binding.bookmarkButton.setImageResource(R.drawable.v_bookmark_added)

                            var imageFile = ""
                            for (i in item.relationships) {
                                if (i.type == "cover_art")
                                    imageFile = i.attributes?.fileName.toString()
                            }
                            val url = "https://uploads.mangadex.org/covers/${item.id}/$imageFile"
                            tvTitle.text = item.attributes.title.en
                            Glide.with(binding.root).load(url).into(ivBookCover)
                        }
                    }
                }

                root.setOnClickListener {
                    onItemClick(item.id)
                }
                binding.bookmarkButton.setOnClickListener {
                    GlobalScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            val client = ApiClient().client
                            val result = client.checkUserFollowedManga(item.id, sessionToken)
                            if (result.code() == 200) {
                                client.deleteMangaFromWishList(item.id, sessionToken)
                                binding.bookmarkButton.setImageResource(R.drawable.v_bookmark_empty)
                            }
                            else {
                                client.addMangaToWishList(item.id, sessionToken)
                                binding.bookmarkButton.setImageResource(R.drawable.v_bookmark_added)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder =
        CollectionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book_for_fragment_collection, parent, false), onItemClick = onItemClick
        )

    override fun getItemCount(): Int = mangaItems.size

    override fun onBindViewHolder(holder: CollectionAdapter.CollectionViewHolder, position: Int) {
        holder.onBind(mangaItems[position], sessionToken)
    }
}