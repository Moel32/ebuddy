package com.moel32.ebuddy

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.fragment.app.ListFragment
import com.moel32.ebuddy.databinding.FragmentSymptomsBinding
import com.moel32.ebuddy.R as Rmoel32


class SymptomsFragment : ListFragment(), AdapterView.OnItemClickListener {

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
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireActivity(), Rmoel32.array.symptoms, R.layout.simple_list_item_multiple_choice)
        listAdapter = adapter
        listView.onItemClickListener = this
        binding.button.setOnClickListener{
            binding.textViewSickness.text = "test"
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(activity, "Item: $position selected", Toast.LENGTH_SHORT).show()
        // change the checkbox state
        val checkedTextView = view as CheckedTextView
        checkedTextView.isChecked = !checkedTextView.isChecked
    }

}
