package com.moel32.ebuddy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverConfigException
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
        //val Intent

        // Initialize the UI elements.
        permissions_button_label = binding.permissionsButton
        action_button_label = binding.actionButton

        //startActivityForResult(Intent(this, PermissionActivity::class.java), 0)

       // Hover.initialize(activity?.applicationContext, this)
        permissions_button_label.setOnClickListener {
            //
            val i = Intent(activity?.applicationContext, PermissionActivity::class.java)
            startActivityForResult(i, 0)
        }

        /*try {
            Hover.requestActionChoice(
                arrayOf<String>("66b8e56a", "df7d3993"),
                object : Hover.ActionChoiceListener() {
                    fun onActionChosen(actionID: String?) {
                        val i = HoverParameters.Builder(this).request(actionID)
                            .buildIntent()
                        startActivityForResult(i, 0)
                    }

                    fun onCanceled() {
                        Toast.makeText(activity, "You must choose a SIM card").show()
                    }
                },
                activity
            )
        } catch (e: HoverConfigException) {
        }*/

        action_button_label.setOnClickListener {
            //
            val i = HoverParameters.Builder(activity)
                .request("66b8e56a") // Add your action ID here
                .request("df7d3993")
                //                    .extra("YOUR_VARIABLE_NAME", "TEST_VALUE") // Uncomment and add your variables if any
                .buildIntent()
            startActivityForResult(i, 0)
        }

        return binding.root
    }
}