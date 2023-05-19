package com.moel32.ebuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.moel32.ebuddy.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.answerButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // find the toast_button by its ID and set a click listener
        //view.findViewById<Button>(R.id.sharing_button).setOnClickListener {
            // create a Toast with some text, to appear for a short time
            //val myToast = Toast.makeText(context, "Sharing Location...", Toast.LENGTH_SHORT)
            // show the Toast
            //myToast.show()
        //}
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}