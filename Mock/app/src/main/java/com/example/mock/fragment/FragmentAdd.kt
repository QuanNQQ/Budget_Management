package com.example.mock.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mock.const.*
import com.example.mock.databinding.FragmentAddBinding
import com.example.mock.model.Budget
import com.example.mock.vm.BudgetViewModel

class FragmentAdd : Fragment() {
    private lateinit var spType: String
    private lateinit var binding: FragmentAddBinding
    private lateinit var mBudgetViewModel: BudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        mBudgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

        // add spinner
        addSpinner()

        // insert to database
        binding.btnAdd.setOnClickListener {
            insertDataToDatabase()
        }

        // backstack
        binding.btnCancel.setOnClickListener {
            popBackStack()
        }

        binding.tvDatetime.setOnClickListener {
            onClickDate()
        }

        return binding.root
    }

    private fun addSpinner() {

        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, type)
        binding.spType.adapter = arrayAdapter
        binding.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                spType = binding.spType.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun insertDataToDatabase() {

        val label = binding.edtLabel.text.toString()
        val description = binding.edtDescription.text.toString()
        val amount = binding.edtAmount.text.toString()
        val date = binding.tvDatetime.text.toString()

        if (inputCheck(label, description, amount, date)) {
            val budget = Budget(0, spType, label, description, amount.toDouble(), date)
            // add data to database
            mBudgetViewModel.addBudget(budget)
            Toast.makeText(context, ADD_SUCCESS, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(context, "please check all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(label: String, description: String, amount: String, date : String): Boolean {
        return !(TextUtils.isEmpty(label) || TextUtils.isEmpty(description) || TextUtils.isEmpty(amount) || TextUtils.isEmpty(date))
    }

    private fun onClickDate() {
        val datePickerFragment = Date_Picker_Dialog()
        val supportFragmentManager = requireActivity().supportFragmentManager

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner) { resultKey, bundle ->
            if (resultKey == REQUEST_KEY) {
                val date = bundle.getString(SELECTED_DATE)
                binding.tvDatetime.text = date
            }
        }
        datePickerFragment.show(supportFragmentManager, DATE_PICKER_FRAGMENT)
    }

}