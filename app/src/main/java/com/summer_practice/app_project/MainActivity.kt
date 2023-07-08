package com.summer_practice.app_project

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller = (supportFragmentManager.findFragmentById(R.id.fcv_container)
                as NavHostFragment).navController
        findViewById<BottomNavigationView>(R.id.bnv_menu).apply {
            setupWithNavController(controller)
        }

        val sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE)
    }
}
