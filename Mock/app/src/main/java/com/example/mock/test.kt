//package com.dotanphu.budgettracker.fragments
//
//import android.app.Dialog
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.dotanphu.budgettracker.activity.MainActivity
//import com.dotanphu.budgettracker.adapter.CustomListAdapter
//import com.dotanphu.budgettracker.adapter.TransactionAdapter
//import com.dotanphu.budgettracker.databinding.CustomDialogBinding
//import com.dotanphu.budgettracker.databinding.FragmentAllTransactionBinding
//import com.dotanphu.budgettracker.model.Transaction
//import com.dotanphu.budgettracker.utils.Constants
//import com.dotanphu.budgettracker.vm.TransactionViewModel
//
//class FragmentAllTransaction : Fragment() {
//    private lateinit var binding: FragmentAllTransactionBinding
//    lateinit var customListDialog: Dialog
//    lateinit var transactionAdapter: TransactionAdapter
//    private val viewModel: TransactionViewModel by viewModels()
//    var oldMyTransaction = arrayListOf<Transaction>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentAllTransactionBinding.inflate(inflater, container, false)
//
//        viewModel.getAllTransaction().observe(viewLifecycleOwner) { transactionList ->
//            if(transactionList.isNotEmpty()) {
//
//                binding.idRVTransactionList.visibility = View.VISIBLE
//                binding.noTransactionsTV.visibility = View.GONE
//
//                oldMyTransaction = transactionList as ArrayList<Transaction>
//                transactionAdapter = TransactionAdapter(requireActivity(), transactionList)
//                binding.idRVTransactionList.adapter = transactionAdapter
//                val staggeredGridLayoutManager =
//                    StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
//                binding.idRVTransactionList.layoutManager = staggeredGridLayoutManager
//            }
//            else{
//                binding.idRVTransactionList.visibility = View.GONE
//                binding.noTransactionsTV.visibility = View.VISIBLE
//            }
//        }
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnFilter.setOnClickListener {
//            filterTransactionsDialog()
//        }
//
////        observeAllTransactions()
//    }
//
//    private fun observeAllTransactions() {
//        viewModel.getAllTransaction().observe(viewLifecycleOwner) { transactions ->
//            run {
//                transactions.let {
//                    if (it.isNotEmpty()) {
//                        binding.idRVTransactionList.visibility = View.VISIBLE
//                        binding.noTransactionsTV.visibility = View.GONE
////                        transactionAdapter.setData(it)
//                        transactionAdapter = TransactionAdapter(requireActivity(), it)
//                        binding.idRVTransactionList.adapter = transactionAdapter
//                        val staggeredGridLayoutManager =
//                            StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
//                        binding.idRVTransactionList.layoutManager = staggeredGridLayoutManager
//                    } else {
//                        binding.idRVTransactionList.visibility = View.GONE
//                        binding.noTransactionsTV.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
//    }
//
//    private fun filterTransactionsDialog() {
//        customListDialog = Dialog(requireActivity())
//        val mBinding = CustomDialogBinding.inflate(layoutInflater)
//        customListDialog.setContentView(mBinding.root)
//        val transactionTypes = Constants.transactionType()
//        transactionTypes.add(0, "All")
//        mBinding.rvList.layoutManager = LinearLayoutManager(requireContext())
//        val customListAdapter = CustomListAdapter(this, transactionTypes)
//        mBinding.rvList.adapter = customListAdapter
//        customListDialog.show()
//    }
//
//    fun filterTransactions(item:String){
//        customListDialog.dismiss()
//        if (item=="All"){
//            binding.toolTV.text=item
//            observeAllTransactions()
//        }
//        else{
//            Log.i("Filter clicked", item)
//            binding.toolTV.text=item
//            viewModel.getFilteredTransactions(item).observe(viewLifecycleOwner){
//                    transactions->
//                run {
//                    transactions.let {
//                        if (it.isNotEmpty()) {
//                            binding.idRVTransactionList.visibility = View.VISIBLE
//                            binding.noTransactionsTV.visibility = View.GONE
//                            transactionAdapter.setData(it)
//
//                        } else {
//                            binding.idRVTransactionList.visibility = View.GONE
//                            binding.noTransactionsTV.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
//        }
//    }
//}