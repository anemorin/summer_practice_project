package com.summer_practice.app_project.Main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.summer_practice.app_project.AppApi.ApiMultiItem
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding : FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.rvMain.layoutManager = LinearLayoutManager(context)
        val listCollections = mutableListOf<ApiMultiItem>()
        val listOfNames = mutableListOf<String>()

        binding.run {
            ibSearch.setOnClickListener {
                NavHostFragment.findNavController(view.findFragment())
                    .navigate(R.id.action_mainFragment_to_searchFragment)
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val client = ApiClient().client
                listOfNames.add("Latest updates")
                listCollections.add(client.getListLatestUpdate(10))
                listOfNames.add("Best in 2023")
                listCollections.add(client.getList2023(10))
                listOfNames.add("All about Naruto")
                listCollections.add(client.getListNaruto(10))
                listOfNames.add("Manga by Oda Eiichiro")
                listCollections.add(client.getListByOdaEiichiro(10))
                listOfNames.add("All about Pokemon")
                listCollections.add(client.getListPokemon(10))
                listOfNames.add("Ninja collection")
                listCollections.add(client.getListNinja(10))
                listOfNames.add("Samurai collection")
                listCollections.add(client.getListSamurai(10))
                val adapter = MainAdapter(listCollections, listOfNames)
                binding.rvMain.adapter = adapter
            }
        }
    }
}