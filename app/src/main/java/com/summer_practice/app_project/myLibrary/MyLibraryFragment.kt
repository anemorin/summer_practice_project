package com.summer_practice.app_project.myLibrary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.summer_practice.app_project.AppApi.ApiClient
import com.summer_practice.app_project.Main.MainAdapter
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentMyLibraryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyLibraryFragment : Fragment(R.layout.fragment_my_library) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyLibraryBinding.bind(view)
        val sharedPreferences = activity?.getSharedPreferences("APP", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("token", null).isNullOrEmpty()) {
                binding.rv.visibility = View.INVISIBLE
            }
            else {
                binding.tvError.visibility = View.INVISIBLE
                val client = ApiClient().client
                GlobalScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        val followList = client.getFollowList(sharedPreferences.getString("token", null).toString())
                        val adapter = MyLibraryAdapter(followList) { item ->
                            NavHostFragment.findNavController(view.findFragment())
                                .navigate(R.id.action_myLibraryFragment_to_comicsPageFragment,
                                    makeBundle(item))
                        }
                        binding.rv.layoutManager = LinearLayoutManager(context)
                        binding.rv.adapter = adapter
                    }
                }
            }
        }
    }

    companion object {
        private fun makeBundle(id: String): Bundle {
            var bundle = Bundle()
            bundle.putString("ID", id)
            return bundle
        }
    }
}