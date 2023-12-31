package com.summer_practice.app_project.Main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.AppApi.MangaItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ComicsItemBinding

class SubMainAdapter(apiItem : ApiMultiItem,
                     private val onItemClick : (String) -> Unit) :
    RecyclerView.Adapter<SubMainAdapter.SubViewHolder>() {
        private val mangaItems = apiItem.data

        class SubViewHolder(
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
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder =
            SubViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.comics_item, parent, false), onItemClick = onItemClick
            )

        override fun getItemCount(): Int = mangaItems.size

        override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
            holder.onBind(mangaItems[position])
        }
}