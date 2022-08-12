package com.example.mock.fragment
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dotanphu.budgettracker.adapter.DialogListAdapter
import com.example.mock.R
import com.example.mock.adapter.BudgetAdapter
import com.example.mock.const.*
import com.example.mock.databinding.CustomDialogBinding
import com.example.mock.databinding.FragmentListBinding
import com.example.mock.vm.BudgetViewModel

class FragmentList : Fragment() {

    private lateinit var binding : FragmentListBinding
    lateinit var customListDialog: Dialog


    private val mBudgetViewModel : BudgetViewModel by viewModels() // this
    private var adapter = BudgetAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        mBudgetViewModel.getTotalAmountByType()
        setRecyclerview()

//        addSpinner()

        setListBudget()

        // go to fragment add
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentList_to_fragmentAdd)
        }

        // delete all budget
        binding.imgTrash.setOnClickListener {
            deleteAllBudget()
        }

        getTotalAmountByType()

        binding.imgType.setOnClickListener {
            filterBudgetDialog()
        }

        return binding.root
    }

    private fun setListBudget() {
        mBudgetViewModel.readAllData.observe(viewLifecycleOwner, Observer { budget ->
            adapter.setData(budget)
        })
    }

    private fun deleteAllBudget() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(YES){ _, _ ->
            mBudgetViewModel.deleteAllBudget()
            mBudgetViewModel.getTotalAmountByType()
            getTotalAmountByType()
            Toast.makeText(requireContext(), TOAST_REMOVE_EVERYTHING_SUCCESS, Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton(NO){ _, _ -> }
        builder.setTitle(TITLE_DELETE_EVERYTHING)
        builder.setMessage(MESSAGE_DELETE_EVERYTHING)
        builder.create().show()
    }

    private fun setRecyclerview(){
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())}

    private fun getTotalAmountByType() {
        mBudgetViewModel.income.observe(viewLifecycleOwner){
            binding.tvDIncome.text = it.toString()

            if(mBudgetViewModel.expense.value != null){
                val balance = mBudgetViewModel.expense.value!! - it
                binding.tvBalance.text = "$ %.1f".format(balance)
            }
        }

        mBudgetViewModel.expense.observe(viewLifecycleOwner){
            binding.tvDExpense.text = it.toString()

            if(mBudgetViewModel.income.value != null){
                val balance = mBudgetViewModel.income.value!! - it
                binding.tvBalance.text = "$ %.1f".format(balance)
            }
        }
    }

    fun filterBudget(item:String){
        customListDialog.dismiss()
        if (item==All){
            binding.tvHistory.text = item
            observeAllBudget()
        }
        else{
            Log.i("Filter clicked", item)
            binding.tvHistory.text=item
            mBudgetViewModel.readAllDataByType(item).observe(viewLifecycleOwner){
                    transactions->
                run {
                    transactions.let {
                        if (it.isNotEmpty()) {
                            binding.recyclerview.visibility = View.VISIBLE
                            adapter.setData(it)

                        } else {
                            binding.recyclerview.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun observeAllBudget() {
        mBudgetViewModel.readAllData.observe(viewLifecycleOwner) { transactions ->
            run {
                transactions.let {
                    if (it.isNotEmpty()) {
                        binding.recyclerview.visibility = View.VISIBLE
                        adapter = BudgetAdapter()
                        adapter.setData(it)
                        binding.recyclerview.adapter = adapter
                        val staggeredGridLayoutManager =
                            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                        binding.recyclerview.layoutManager = staggeredGridLayoutManager
                    } else {
                        binding.recyclerview.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun filterBudgetDialog() {
        customListDialog = Dialog(requireActivity())
        val mBinding = CustomDialogBinding.inflate(layoutInflater)
        customListDialog.setContentView(mBinding.root)
        val budgetType = Constants.transactionType()
        budgetType.add(0, All)
        mBinding.rvList.layoutManager = LinearLayoutManager(requireContext())
        val customListAdapter = DialogListAdapter(this, budgetType)
        mBinding.rvList.adapter = customListAdapter
        customListDialog.show()

    }

}