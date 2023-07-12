package com.summer_practice.app_project.Main

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.CollectionItemBinding

class MainAdapter(
    private val listCollections : List<ApiMultiItem>,
    private val listOfNames : List<String>) :
    RecyclerView.Adapter<MainAdapter.MainFragmentViewHolder>() {

    class MainFragmentViewHolder(private val view: View):
        RecyclerView.ViewHolder(view) {

        private val binding = CollectionItemBinding.bind(view)
        fun onBind(apiMultiItem: ApiMultiItem, titleName: String) {
            binding.run {
                tvCollectionLabel.text = titleName
                bMore.setOnClickListener {
                    findNavController(view.findFragment())
                        .navigate(R.id.action_mainFragment_to_collectionFragment,
                            makeBundle(titleName))
                }
                val adapter = SubMainAdapter(apiMultiItem) { item ->
                    findNavController(view.findFragment())
                        .navigate(R.id.action_mainFragment_to_comicsPageFragment,
                            makeBundle(item))}
                rvHorizontal.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentViewHolder =
        MainFragmentViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item, parent, false))

    override fun getItemCount(): Int = listCollections.size

    override fun onBindViewHolder(holder: MainFragmentViewHolder, position: Int) {
        holder.onBind(listCollections[position], listOfNames[position])
    }

    companion object {
        private fun makeBundle(id : String): Bundle {
            val bundle = Bundle()
            bundle.putString("ID", id)
            return bundle
        }

        private fun makeParcelabelBundle(apiData : Parcelable, name : String) : Bundle {
            val bundle = Bundle()
            bundle.putParcelable("API", apiData)
            bundle.putString("NAME", name)
            return bundle
        }
    }
}