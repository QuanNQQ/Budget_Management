package com.example.mock.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mock.R
import com.example.mock.const.*
import com.example.mock.databinding.FragmentUpdateBinding
import com.example.mock.model.Budget
import com.example.mock.vm.BudgetViewModel


class FragmentUpdate : Fragment() {
    private lateinit var binding : FragmentUpdateBinding
    private val args by navArgs<FragmentUpdateArgs>()
    private val mBudgetViewModel : BudgetViewModel by viewModels() // this


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        setData()

        binding.tvUpDatetime.setOnClickListener{
            onClickDate()
        }

        // choose the date
        binding.btnUpdate.setOnClickListener{
            updateBudget()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imgTrash.setOnClickListener{
            deleteBudget()
        }
        // add menu
        setHasOptionsMenu(true)

        return binding.root
    }
    // set Data
    private fun setData() {

        binding.edtUpType.setText(args.currentBudget.type)
        binding.edtUpLabel.setText(args.currentBudget.label)
        binding.edtUpDescription.setText(args.currentBudget.description)
        binding.edtUpAmount.setText(args.currentBudget.amount.toString())
        binding.tvUpDatetime.text = args.currentBudget.datetime
    }


    private fun updateBudget() {

        val label = binding.edtUpLabel.text.toString()
        val description = binding.edtUpDescription.text.toString()
        val amount = binding.edtUpAmount.text.toString().toDouble()
        val date = binding.tvUpDatetime.text.toString()
        val type = binding.edtUpType.text.toString()

        if (inputCheck(label, description, amount)){

            // object budget
            val updateBudget = Budget(args.currentBudget.id, type, label, description, amount, date)

            // update Current Budget
            mBudgetViewModel.updateBudget(updateBudget)
            Toast.makeText(requireContext(), TOAST_UPDATE_SUCCESS, Toast.LENGTH_LONG).show()

            //Navigate back
            findNavController().popBackStack()
        }
        else {
            Toast.makeText(requireContext(), TOAST_UPDATE_FAIL, Toast.LENGTH_LONG).show()

        }
    }

    private fun inputCheck(label: String, description : String, amount : Double ) : Boolean{
        return !(TextUtils.isEmpty(label) && TextUtils.isEmpty(description) && amount.equals(""))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete)
            deleteBudget()
        return super.onOptionsItemSelected(item)
    }

    // delete current budget
    private fun deleteBudget() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(YES){ _, _ ->
            mBudgetViewModel.deleteBudget(args.currentBudget)
            Toast.makeText(requireContext(), TOAST_REMOVE_SUCCESS + " ${args.currentBudget.label}", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton(NO){ _, _ -> }
        builder.setTitle(TITLE_DELETE +" ${args.currentBudget.label}?")
        builder.setMessage( MESSAGE_DELETE_BUDGET + " ${args.currentBudget.label}")
        builder.create().show()
    }

    // choose the date
    private fun onClickDate(){
        val datePickerFragment = Date_Picker_Dialog()
        val supportFragmentManager = requireActivity().supportFragmentManager
        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner)
        {
                resultKey, bundle ->
            if(resultKey == REQUEST_KEY){

                val date = bundle.getString(SELECTED_DATE)
                binding.tvUpDatetime.text = date
            }
        }
        datePickerFragment.show(supportFragmentManager, DATE_PICKER_FRAGMENT)

    }

}