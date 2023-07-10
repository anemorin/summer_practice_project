package com.summer_practice.app_project

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("APP",Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            if (sharedPreferences.getString("token", null).isNullOrEmpty()) {
            }

        }
    }
}