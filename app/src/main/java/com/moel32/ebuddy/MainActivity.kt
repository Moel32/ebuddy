package com.moel32.ebuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.moel32.ebuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListener()
        loadFragment(FirstFragment())
    }

    private fun setupBottomNavigationView() {
        setupClickListener()
        binding.bottomNavigationView.getOrCreateBadge(R.id.floatingActionButtonMessage)
        binding.bottomNavigationView.getBadge(R.id.floatingActionButtonMessage)?.apply {
            number = 0
        }
    }

    private fun setupClickListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {

            val fragment = when (it.itemId) {
                R.id.floatingActionButtonMessage -> {
                    removeBadge(it.itemId)
                    SecondFragment()
                }
                R.id.floatingActionButtonShare -> {
                    ThirdFragment()
                }
                R.id.floatingActionButtonLibrary -> {
                    FourthFragment()
                }
                R.id.Settings -> {
                    FifthFragment()
                }
                else -> {
                    FirstFragment()
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
            .replace(R.id.container, fragment)
            .commit()
    }
}