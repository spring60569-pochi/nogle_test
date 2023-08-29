package com.pochi.nogletest

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pochi.nogletest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBar: ActionBar
    private lateinit var mainBottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainBottomNavigationView = binding.navView
        mainBottomNavigationView.itemIconTintList = null

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_a,
                R.id.navigation_b,
                R.id.navigation_c,
                R.id.navigation_d
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        mainBottomNavigationView.setupWithNavController(navController)
    }

    fun setActionbarVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) {
            actionBar.show()
        } else {
            actionBar.hide()
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        mainBottomNavigationView.visibility = visibility
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}