package com.summer_practice.app_project.Reader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.summer_practice.app_project.AppApi.ChapterImageItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.ImagePageItemBinding

class ReaderAdapter(apiItem : ChapterImageItem) :
    RecyclerView.Adapter<ReaderAdapter.ReaderViewHolder>() {

    val chapterImage = apiItem.chapter.data
    val url = apiItem.baseUrl + "/data/" + apiItem.chapter.hash + "/"
    class ReaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ImagePageItemBinding.bind(view)
        fun onBind(item: String, url: String) {
            Glide.with(binding.root).load(url + item).into(binding.ivPageImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReaderAdapter.ReaderViewHolder =
        ReaderAdapter.ReaderViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.image_page_item, parent, false))

    override fun getItemCount(): Int = chapterImage.size

    override fun onBindViewHolder(holder: ReaderAdapter.ReaderViewHolder, position: Int) {
        holder.onBind(chapterImage[position], url)
    }
}