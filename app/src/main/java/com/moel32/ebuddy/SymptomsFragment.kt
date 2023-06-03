package com.moel32.ebuddy

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.moel32.ebuddy.databinding.ActivityMainBinding
import com.moel32.ebuddy.databinding.FragmentChatBinding
import com.moel32.ebuddy.databinding.FragmentSymptomsBinding
import com.moel32.ebuddy.R as Rmoel32

import com.moel32.ebuddy.helpers.APIUtils
import com.moel32.ebuddy.helpers.Illness
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.util.logging.Logger

class SymptomsFragment  : Fragment() {

        private lateinit var binding: FragmentSymptomsBinding
        private lateinit var symptomInput: EditText
        private lateinit var checkButton: Button
        private lateinit var resetButton: Button
        private lateinit var illnessList: TextView

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

            symptomInput = binding.symptomInput
            checkButton = binding.checkButton
            resetButton = binding.resetButton
            illnessList = binding.illnessList

            checkButton.setOnClickListener {
                val symptom = symptomInput.text.toString()
                APIUtils.getIllnesses(symptom, object : Callback<List<Illness>> {
                    override fun onResponse(
                        call: Call<List<Illness>>,
                        response: Response<List<Illness>>
                    ) {
                        if (response.isSuccessful) {
                            val illnesses = response.body()
                            displayIllnesses(illnesses)
                        } else {
                            showError()
                        }
                    }

                    override fun onFailure(call: Call<List<Illness>>, t: Throwable) {

                        showError()
                    }
                })
            }

            resetButton.setOnClickListener {
                resetSymptomChecker()
            }
        }

        private fun displayIllnesses(illnesses: List<Illness>?) {
            val sb = StringBuilder()
            if (!illnesses.isNullOrEmpty()) {
                for (illness in illnesses) {
                    sb.append(illness.condition).append("\n\n")
                }
            } else {
                sb.append("No illnesses found.")
            }
            illnessList.text = sb.toString()
        }

        private fun showError() {
            Toast.makeText(activity, "An error occurred. Please try again later.", Toast.LENGTH_SHORT)
                .show()
        }

        private fun resetSymptomChecker() {
            symptomInput.setText("")
            illnessList.text = ""
        }
}

