package com.moel32.ebuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.moel32.ebuddy.databinding.FragmentSymptomsBinding
import com.moel32.ebuddy.helpers.SymptomChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.github.kittinunf.result.Result
import android.widget.*


/**
 * A fragment for performing symptom checks using the Apimedic API.
 * Performs a symptom check using the Apimedic API and displays the results in a [TextView].
 */
class SymptomsFragment : Fragment() {

    private val checker = SymptomChecker()

    // Define the map of symptoms that the user can select from.
    private val symptomMap = mapOf(
        1 to "Fever",
        2 to "Cough",
        3 to "Shortness of breath",
        4 to "Fatigue",
        5 to "Headache",
        6 to "Muscle or body aches",
        7 to "New loss of taste or smell",
        8 to "Sore throat",
        9 to "Congestion or runny nose",
        10 to "Nausea or vomiting",
        11 to "Diarrhea"
    )

    // Define the IDs of the symptoms that the user selects.
    private val symptomIds = mutableSetOf<Int>()

    // Define the binding and UI elements.
    private lateinit var binding: FragmentSymptomsBinding
    private lateinit var symptomSpinner: Spinner
    private lateinit var ageEditText: EditText
    private lateinit var sexRadioGroup: RadioGroup
    private lateinit var maleRadioButton: RadioButton
    private lateinit var femaleRadioButton: RadioButton
    private lateinit var searchButton: Button
    private lateinit var resetButton: Button
    private lateinit var resultView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSymptomsBinding.inflate(inflater, container, false)

        val view = binding.root

        // Initialize the UI elements.
        symptomSpinner = binding.symptomSpinner
        ageEditText = binding.ageEditText
        sexRadioGroup = binding.sexRadioGroup
        maleRadioButton = binding.maleRadioButton
        femaleRadioButton = binding.femaleRadioButton
        searchButton = binding.searchButton
        resetButton = binding.resetButton
        resultView = binding.resultTextView

        // Set up the symptom spinner.
        val symptomAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, symptomMap.values.toList())
        symptomSpinner.adapter = symptomAdapter
        symptomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Add the selected symptom ID to the set.
                val symptomId = symptomMap.keys.toList()[position]
                symptomIds.add(symptomId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing.
            }
        }

        searchButton.setOnClickListener {

            // Get the age and sex inputs.
            val age = ageEditText.text.toString().toIntOrNull()
            val sex = when (sexRadioGroup.checkedRadioButtonId) {
                R.id.male_radio_button -> "male"
                R.id.female_radio_button -> "female"
                else -> null
            }

            if (symptomIds.isNotEmpty()) {
                binding.resultTextView.text = "Checking symptoms..."
                CoroutineScope(Dispatchers.IO).launch {
                    val selectedIds = symptomIds.toList();
                    val result = checker.checkSymptoms(selectedIds, age, sex)
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is Result.Success -> {
                                val issues = result.value.issues
                                if (issues.isNotEmpty()) {
                                    val issueNames = issues.joinToString(", ") { it.name }
                                    resultView.text = "Possible health issues: $issueNames"
                                } else {
                                    resultView.text = "No health issues found"
                                }
                            }
                            is Result.Failure -> {
                                val error = result.error
                                resultView.text = "Error: ${error.exception.message}"
                            }
                            else -> {
                                resultView.text = "Unexpected result: $result"
                            }
                        }
                    }
                }
            } else {
                resultView.text = "Please enter at least one symptom."
            }
        }

        // Set up the reset button.
        resetButton.setOnClickListener {
            // Clear the symptom IDs and reset the UI.
            symptomIds.clear()
            symptomSpinner.setSelection(0)
            ageEditText.setText("")
            sexRadioGroup.clearCheck()
        }

        return binding.root
    }
}