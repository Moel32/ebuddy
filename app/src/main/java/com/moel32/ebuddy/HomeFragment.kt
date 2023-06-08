package com.moel32.ebuddy

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.hover.sdk.permissions.PermissionActivity
import com.moel32.ebuddy.databinding.FragmentHomeBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@Suppress("DEPRECATION")
class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding
    private val TAG = "HomeFragment"

    private lateinit var permissions_button_label: Button
    private lateinit var action_button_label: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root

        // Initialize the UI elements.
        permissions_button_label = binding.permissionsButton
        action_button_label = binding.actionButton

       // Hover.initialize(activity?.applicationContext, this)
        permissions_button_label.setOnClickListener {
            //
            val i = Intent(activity?.applicationContext, HomeFragment::class.java)
            startActivityForResult(i, 0)
        }

        action_button_label.setOnClickListener {
            //
            val i = HoverParameters.Builder(activity)
                .request("YOUR_ACTION_ID") // Add your action ID here
                //                    .extra("YOUR_VARIABLE_NAME", "TEST_VALUE") // Uncomment and add your variables if any
                .buildIntent()
            startActivityForResult(i, 0)
        }

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}