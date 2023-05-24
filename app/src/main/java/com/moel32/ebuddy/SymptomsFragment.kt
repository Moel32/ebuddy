package com.moel32.ebuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moel32.ebuddy.databinding.FragmentSymptomsBinding

class SymptomsFragment : Fragment() {
    private lateinit var binding: FragmentSymptomsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSymptomsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    private fun setupData() {
    }


}