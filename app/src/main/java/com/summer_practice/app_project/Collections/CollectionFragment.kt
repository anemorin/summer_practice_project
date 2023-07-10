package com.summer_practice.app_project.Collections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment(R.layout.fragment_collection) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiItem : ApiMultiItem = arguments?.getParcelable("API")!!
        val name = arguments?.getString("NAME")
        val binding = FragmentCollectionBinding.bind(view)
        binding.tvTitle.text = name
        binding.rv.layoutManager = LinearLayoutManager(context)
        val adapter = CollectionAdapter(apiItem) {item ->
            NavHostFragment.findNavController(view.findFragment())
            .navigate(R.id.action_collectionFragment_to_comicsPageFragment,
                makeBundle(item))
        }
        binding.rv.adapter = adapter
    }

    companion object {
        private fun makeBundle(id : String): Bundle {
            var bundle = Bundle()
            bundle.putString("ID", id)
            return bundle
        }
    }
}