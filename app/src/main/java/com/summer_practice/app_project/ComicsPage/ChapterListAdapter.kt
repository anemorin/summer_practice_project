package com.summer_practice.app_project.ComicsPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.summer_practice.app_project.AppApi.ApiItemChapter
import com.summer_practice.app_project.AppApi.ChapterItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ChapterItemBinding

class ChapterListAdapter(apiItem : ApiItemChapter,
                         private val onItemClick : (String) -> Unit) :
    RecyclerView.Adapter<ChapterListAdapter.ChapterListViewHolder>() {

    private val chaptersItems = apiItem.data
    
    class ChapterListViewHolder(
        view: View,
        private val onItemClick : (String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val binding = ChapterItemBinding.bind(view)
        fun onBind(item: ChapterItem) {
            binding.run {
                if (!(item.attributes.volume).isNullOrEmpty())
                    binding.tvVolume.text = item.attributes.volume.toString()
                else binding.tvVolume.text = "1"

                if (!(item.attributes.chapter).isNullOrEmpty())
                    binding.tvChapterNum.text = item.attributes.chapter.toString()
                else binding.tvChapterNum.text = ""

                if (!(item.attributes.title).isNullOrEmpty())
                    binding.tvChapterName.text = item.attributes.title.toString()
                else binding.tvChapterName.text = ""

                root.setOnClickListener {
                    onItemClick(item.id)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterListViewHolder =
        ChapterListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chapter_item, parent, false), onItemClick = onItemClick
        )

    override fun getItemCount(): Int = chaptersItems.size

    override fun onBindViewHolder(holder: ChapterListViewHolder, position: Int) {
        holder.onBind(chaptersItems[position])
    }
}