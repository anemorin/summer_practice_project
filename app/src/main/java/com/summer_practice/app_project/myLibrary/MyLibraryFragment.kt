package com.summer_practice.app_project.myLibrary

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.summer_practice.app_project.R
import com.summer_practice.app_project.databinding.FragmentMyLibraryBinding

class MyLibraryFragment : Fragment(R.layout.fragment_my_library) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyLibraryBinding.bind(view)
        val sharedPreferences = activity?.getSharedPreferences("APP", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("token", null).isNullOrEmpty()) {
                binding.rv.visibility = view.visibility.inv()
            }
            else {
                binding.tvError.visibility = view.visibility.inv()
            }
        }
    }
}