package com.summer_practice.app_project.Main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.summer_practice.app_project.AppApi.ApiItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.CollectionItemBinding

class MainAdapter(
    private val listCollections : List<ApiItem>) :
    RecyclerView.Adapter<MainAdapter.MainFragmentViewHolder>() {

    class MainFragmentViewHolder(private val view: View):
        RecyclerView.ViewHolder(view) {
        private val binding = CollectionItemBinding.bind(view)
        fun onBind(item: ApiItem) {
            binding.run {
                val adapter = subMainAdapter(item) { item ->
                    findNavController(view.findFragment())
                        .navigate(R.id.action_mainFragment_to_comicsPageFragment)}
                binding.rvHorizontal.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder =
        MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item, parent, false))

    override fun getItemCount(): Int = listCollections.size

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.onBind(listCollections[position])
    }
}