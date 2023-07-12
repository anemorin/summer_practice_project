package com.summer_practice.app_project.Collections

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentCollectionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectionFragment : Fragment(R.layout.fragment_collection) {
    private lateinit var binding :FragmentCollectionBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCollectionBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = activity?.getSharedPreferences("APP", Context.MODE_PRIVATE);
        val name = arguments?.getString("ID")
        val client = ApiClient().client
        binding.tvTitle.text = name
        binding.rv.layoutManager = LinearLayoutManager(context)
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                var apiItem : ApiMultiItem? = client.getListLatestUpdate(20)
                if (name == "Best in 2023") {
                    apiItem = client.getList2023(20)
                }
                if (name == "All about Naruto") {
                    apiItem = client.getListNaruto(20)
                }
                if (name == "Manga by Oda Eiichiro") {
                    apiItem = client.getListByOdaEiichiro(20)
                }
                if (name == "All about Pokemon") {
                    apiItem = client.getListPokemon(20)
                }
                if (name == "Ninja collection") {
                    apiItem = client.getListNinja(20)
                }
                if (name == "Samurai collection") {
                    apiItem = client.getListSamurai(20)
                }
                var adapter : CollectionAdapter
                if (sharedPreferences?.getString("token", null) == null) {
                    adapter = CollectionAdapter(apiItem!!, "") { item ->
                        NavHostFragment.findNavController(view.findFragment())
                            .navigate(
                                R.id.action_collectionFragment_to_comicsPageFragment,
                                makeBundle(item)
                            )
                    }
                }
               else {
                    adapter = CollectionAdapter(apiItem!!,
                        sharedPreferences?.getString("token", null)!!
                    ) { item ->
                        NavHostFragment.findNavController(view.findFragment())
                            .navigate(R.id.action_collectionFragment_to_comicsPageFragment,
                                makeBundle(item))
                    }
               }
                binding.rv.adapter = adapter
            }
        }

    }

    companion object {
        private fun makeBundle(id : String): Bundle {
            var bundle = Bundle()
            bundle.putString("ID", id)
            return bundle
        }
    }
}