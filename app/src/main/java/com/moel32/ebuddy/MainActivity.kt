@file:Suppress("DEPRECATION")

package com.moel32.ebuddy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.moel32.ebuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            handleBottomNavigation(
                it.itemId
            )
        }
        binding.bottomNavigationView.selectedItemId = R.id.floatingActionButtonHome
    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean = when(menuItemId) {
        R.id.floatingActionButtonMessage ->{
            swapFragments(SecondFragment())
            true
        }
        R.id.floatingActionButtonShare -> {
            swapFragments(ThirdFragment())
            true
        }
        R.id.floatingActionButtonLibrary -> {
            swapFragments(FourthFragment())
            true
        }
        else -> false
    }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.firstFragment, fragment)
            .commit()
    }
}