package com.moel32.ebuddy

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ArrayAdapter
    import androidx.fragment.app.Fragment
    import com.google.android.material.snackbar.Snackbar
    import com.moel32.ebuddy.databinding.FragmentSymptomsBinding
    import com.moel32.ebuddy.helpers.SymptomChecker
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext
    import com.github.kittinunf.result.Result

/**
 * A fragment for performing symptom checks using the Apimedic API.
 * Performs a symptom check using the Apimedic API and displays the results in a [TextView].
 */
    class SymptomsFragment : Fragment() {

        private lateinit var binding: FragmentSymptomsBinding
        private val checker = SymptomChecker()

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentSymptomsBinding.inflate(inflater, container, false)

            binding.checkButton.setOnClickListener {
                val symptoms = binding.symptomEditText.text.toString().split(",")
                val age = binding.ageEditText.text.toString().toIntOrNull()
                val sex = binding.sexEditText.text.toString()
                if (symptoms.isNotEmpty()) {
                    binding.resultTextView.text = "Checking symptoms..."
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = checker.checkSymptoms(symptoms, age, sex)
                        withContext(Dispatchers.Main) {
                            when (result) {
                                is Result.Success -> {
                                    val issues = result.value.issues
                                    if (issues.isNotEmpty()) {
                                        val issueNames = issues.joinToString(", ") { it.name }
                                        binding.resultTextView.text = "Possible health issues: $issueNames"
                                    } else {
                                        binding.resultTextView.text = "No health issues found"
                                    }
                                }
                                is Result.Failure -> {
                                    val error = result.error
                                    binding.resultTextView.text = "Error: ${error.exception.message}"
                                }
                                else -> {
                                    binding.resultTextView.text = "Unexpected result: $result"
                                }
                            }
                        }
                    }
                } else {
                    binding.resultTextView.text = "Please enter at least one symptom."
                }
            }

            binding.resetButton.setOnClickListener {
                binding.symptomEditText.text.clear()
                binding.ageEditText.text.clear()
                binding.sexEditText.text.clear()
                binding.resultTextView.text = ""
            }

            return binding.root
        }
    }