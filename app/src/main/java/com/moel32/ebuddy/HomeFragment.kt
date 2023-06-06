package com.moel32.ebuddy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moel32.ebuddy.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        // Get the gender value selected by the user
        val genderSpinner = binding.genderSpinner
        val gender = genderSpinner.selectedItem.toString()

        // Get the year of birth value selected by the user
        val yearPicker = binding.yearPicker
        val year = yearPicker.text

        // Store the gender and year of birth values in SharedPreferences
        val sharedPrefs = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("gender", gender)
        editor.putInt("year", 2000)
        editor.apply()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}