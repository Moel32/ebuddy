package com.moel32.ebuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import com.moel32.ebuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListener()
        loadFragment(HomeFragment())
    }

    private fun setupClickListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {

            val fragment = when (it.itemId) {
                R.id.floatingActionButtonSymptoms -> {
                    SymptomsFragment()
                }
                R.id.floatingActionButtonMessage -> {
                    ChatFragment()
                }
                R.id.floatingActionButtonShare -> {
                    MapFragment()
                }
                else -> {
                    HomeFragment()
                }
            }
            loadFragment(fragment)
            true
        }
    }

    private fun removeBadge(badgeId: Int) {
        binding.bottomNavigationView.getBadge(badgeId)?.let { badgeDrawable ->
            if (badgeDrawable.isVisible) {
                binding.bottomNavigationView.removeBadge(badgeId)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.drawer_layout, fragment)
            .commit()
    }
}