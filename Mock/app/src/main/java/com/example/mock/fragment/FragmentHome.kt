package com.example.mock.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mock.R
import com.example.mock.const.*
import com.example.mock.databinding.FragmentHomeBinding
import com.example.mock.vm.BudgetViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.ArrayList

class FragmentHome : Fragment() {
    private lateinit var pieChart: PieChart
    private lateinit var binding : FragmentHomeBinding
    private val mBudgetViewModel : BudgetViewModel by viewModels() // this
    private var income : Float = 0f
    private var expense : Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        pieChart = binding.pieChart

        mBudgetViewModel.getTotalAmountByType()
        getTotalAmountByType()

        setupPieChart()
        loadPieChartData(income, expense)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentList)
        }

        return binding.root
    }

    private fun getTotalAmountByType() {
        mBudgetViewModel.income.observe(viewLifecycleOwner){
            binding.tvDIncome.text = it.toString()
            income = it.toFloat()
            loadPieChartData(income, expense)
            if(mBudgetViewModel.expense.value != null){
                val balance = mBudgetViewModel.expense.value!! - it
                binding.tvDBalance.text = "$ %.1f".format(balance)
            }
        }

        mBudgetViewModel.expense.observe(viewLifecycleOwner){
            binding.tvDExpense.text = it.toString()
            expense = it.toFloat()

            loadPieChartData(income, expense)
            if(mBudgetViewModel.income.value != null){
                val balance = mBudgetViewModel.income.value!! - it
                binding.tvDBalance.text = "$ %.1f".format(balance)
            }
        }
    }

    private fun setupPieChart() {

        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = BALANCE
        pieChart.setCenterTextSize(13f)

//
        pieChart.description.isEnabled = false
        val l = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }

    private fun loadPieChartData(income: Float, expense : Float) {

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(income, INCOME))
        entries.add(PieEntry(expense, EXPENSE))
        val colors = ArrayList<Int>()
        colors.add(Color.GREEN)
        colors.add(Color.RED)
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
    }
}