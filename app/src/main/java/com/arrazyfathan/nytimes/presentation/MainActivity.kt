package com.arrazyfathan.nytimes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.arrazyfathan.nytimes.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NYTimes)
        setContentView(R.layout.activity_main)

        navController.findNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.findNavController().navigateUp() || super.onSupportNavigateUp()
    }
}
