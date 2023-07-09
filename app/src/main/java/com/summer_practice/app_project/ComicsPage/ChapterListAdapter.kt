package com.summer_practice.app_project.ComicsPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ApiItemChapter
import com.summer_practice.app_project.AppApi.ChapterItem
import com.summer_practice.app_project.AppApi.MangaItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ComicsItemBinding
import com.summer_practice.app_project.databinding.FragmentResyclerviewElementBinding

class ChapterListAdapter(private val apiItem : ApiItemChapter,
                         private val onItemClick : (String) -> Unit) :
    RecyclerView.Adapter<ChapterListAdapter.ChapterListViewHolder>() {
    private val chaptersItems = apiItem.data

    class ChapterListViewHolder(
        private val view: View,
        private val onItemClick : (String) -> Unit) :
        RecyclerView.ViewHolder(view) {
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
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterListViewHolder =
        ChapterListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comics_item, parent, false), onItemClick = onItemClick
        )

    override fun getItemCount(): Int = chaptersItems.size

    override fun onBindViewHolder(holder: ChapterListViewHolder, position: Int) {
        holder.onBind(chaptersItems[position])
    }
}