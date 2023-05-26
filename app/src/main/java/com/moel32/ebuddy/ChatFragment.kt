package com.moel32.ebuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moel32.ebuddy.databinding.FragmentChatBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setupData()
    }

   /* private fun setupData() {
        binding.myChatView.com = getString(R.string.chat_bot)
    }*/
}