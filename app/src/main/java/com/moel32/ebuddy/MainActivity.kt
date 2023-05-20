package com.moel32.ebuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.moel32.ebuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /*var mAddShareFab: FloatingActionButton? = null
    var mAddLibraryFab: FloatingActionButton? = null
    var mAddImportantFab: FloatingActionButton? = null

    var mAddHomeFab: ExtendedFloatingActionButton? = null
    var isAllFabsVisible: Boolean? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*mAddHomeFab = findViewById(R.id.floatingActionButtonHome)

        mAddShareFab = findViewById(R.id.floatingActionButtonShare)
        mAddLibraryFab = findViewById(R.id.floatingActionButtonLibrary)
        mAddImportantFab = findViewById(R.id.floatingActionButtonImportant)

        mAddShareFab.setVisibility(View.GONE)
        mAddLibraryFab.setVisibility(View.GONE)
        mAddImportantFab.setVisibility(View.GONE)

        isAllFabsVisible = false

        mAddHomeFab.shrink()

        mAddHomeFab.setOnClickListener(
         object : View.OnClickListener() {
                override fun onClick(view: View?) {
                    isAllFabsVisible = if (!isAllFabsVisible!!) {

                        mAddShareFab.show()
                        mAddLibraryFab.show()
                        mAddImportantFab.show()

                        mAddHomeFab.extend()

                        true
                    } else {

                        mAddShareFab.hide()
                        mAddLibraryFab.hide()
                        mAddImportantFab.hide()

                        mAddHomeFab.shrink()

                        false
                    }
                }
            })

        mAddLibraryFab.setOnClickListener(
            object : View.OnClickListener() {
                override fun onClick(view: View?) {
                    Toast.makeText(this@MainActivity, "Library Added", Toast.LENGTH_SHORT).show()
                }
            })

        mAddShareFab.setOnClickListener(
            object : View.OnClickListener() {
                override fun onClick(view: View?) {
                    Toast.makeText(this@MainActivity, "Location Added", Toast.LENGTH_SHORT).show()
                }
            })

        mAddImportantFab.setOnClickListener(
            object : View.OnClickListener() {
                override fun onClick(view: View?) {
                    Toast.makeText(this@MainActivity, "Important Added", Toast.LENGTH_SHORT).show()
                }
            })
    }*/
    }
}