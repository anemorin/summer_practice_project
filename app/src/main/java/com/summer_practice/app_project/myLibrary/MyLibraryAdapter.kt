package com.summer_practice.app_project.myLibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.AppApi.MangaItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ComicsItemBinding

class MyLibraryAdapter(apiItem : ApiMultiItem,
                     private val onItemClick : (String) -> Unit) :
    RecyclerView.Adapter<MyLibraryAdapter.MyLibraryViewHolder>() {
        private val mangaItems = apiItem.data

    class MyLibraryViewHolder(
        view: View,
        private val onItemClick : (String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val binding = ComicsItemBinding.bind(view)
        fun onBind(item: MangaItem) {
            binding.run {
                var imageFile = ""
                for (i in item.relationships) {
                    if (i.type == "cover_art")
                        imageFile = i.attributes?.fileName.toString()
                }
                val url = "https://uploads.mangadex.org/covers/${item.id}/$imageFile"
                binding.tvHeader.text = item.attributes.title.en
                Glide.with(binding.root).load(url).into(ivBookCover)

                root.setOnClickListener {
                    onItemClick(item.id)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLibraryViewHolder =
        MyLibraryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book_for_fragment_collection, parent, false), onItemClick = onItemClick
        )

    override fun getItemCount(): Int = mangaItems.size

    override fun onBindViewHolder(holder: MyLibraryViewHolder, position: Int) {
        holder.onBind(mangaItems[position])
    }
}