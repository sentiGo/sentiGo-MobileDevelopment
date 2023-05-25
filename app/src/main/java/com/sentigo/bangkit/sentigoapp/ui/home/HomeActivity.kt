package com.sentigo.bangkit.sentigoapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.databinding.ActivityHomeBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = ViewModelFactory.getInstance(this)

        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarCompatActivity = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_find, R.id.nav_favorite, R.id.nav_profile
        ).build()

        setupActionBarWithNavController(navController, appBarCompatActivity)
        navView.setupWithNavController(navController)
    }
}