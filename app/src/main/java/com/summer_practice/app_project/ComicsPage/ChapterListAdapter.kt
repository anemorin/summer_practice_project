package com.summer_practice.app_project.ComicsPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ApiItemChapter
<<<<<<< HEAD
import com.summer_practice.app_project.AppApi.ChapterItem
import com.summer_practice.app_project.AppApi.MangaItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ComicsItemBinding
import com.summer_practice.app_project.databinding.FragmentResyclerviewElementBinding
=======
import com.summer_practice.app_project.AppApi.MangaItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ComicsItemBinding
>>>>>>> origin/main

class ChapterListAdapter(private val apiItem : ApiItemChapter,
                         private val onItemClick : (String) -> Unit) :
    RecyclerView.Adapter<ChapterListAdapter.ChapterListViewHolder>() {
<<<<<<< HEAD
    private val chaptersItems = apiItem.data
=======
    private val mangaItems = apiItem.data
>>>>>>> origin/main

    class ChapterListViewHolder(
        private val view: View,
        private val onItemClick : (String) -> Unit) :
        RecyclerView.ViewHolder(view) {
<<<<<<< HEAD
        private val binding = FragmentResyclerviewElementBinding.bind(view)
        fun onBind(item: ChapterItem) {
            binding.run {
                if (item.attributes.translatedLanguage == "en") {
                    binding.tvVol.text = item.attributes.volume
                    binding.tvChapterNumber.text = item.attributes.chapter
                    binding.tvChapterLabel.text = R.string.chapters_label.toString()
                    if (item.attributes.title != null)
                        binding.tvPartName.text = item.attributes.title
                    root.setOnClickListener {
                        onItemClick(item.id)
                    }
=======
        private val binding = ComicsItemBinding.bind(view)
        fun onBind(item: MangaItem) {
            binding.run {
                var imageFile: String = ""
                for (i in item.relationships) {
                    if (i.type == "cover_art")
                        imageFile = i.attributes?.fileName.toString()
                }
                val url = "https://uploads.mangadex.org/covers/${item.id}/$imageFile"
                binding.tvHeader.text = item.attributes.title.en
                Glide.with(binding.root).load(url).into(ivBookCover)

                root.setOnClickListener {
                    onItemClick(item.id)
>>>>>>> origin/main
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterListViewHolder =
        ChapterListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comics_item, parent, false), onItemClick = onItemClick
        )

<<<<<<< HEAD
    override fun getItemCount(): Int = chaptersItems.size

    override fun onBindViewHolder(holder: ChapterListViewHolder, position: Int) {
        holder.onBind(chaptersItems[position])
=======
    override fun getItemCount(): Int = mangaItems.size

    override fun onBindViewHolder(holder: ChapterListViewHolder, position: Int) {
        holder.onBind(mangaItems[position])
>>>>>>> origin/main
    }
}