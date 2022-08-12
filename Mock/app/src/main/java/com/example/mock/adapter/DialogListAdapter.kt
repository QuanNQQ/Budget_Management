package com.dotanphu.budgettracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mock.databinding.ItemCustomDialogBinding
import com.example.mock.fragment.FragmentList

class DialogListAdapter(val fragment: Fragment, private val customList:List<String>): RecyclerView.Adapter<DialogListAdapter.ViewHolder>() {

    class ViewHolder(itemView: ItemCustomDialogBinding):RecyclerView.ViewHolder(itemView.root) {
        val title=itemView.tvText

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemCustomDialogBinding.inflate(LayoutInflater.from(fragment.requireContext()),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=customList[position]
        holder.title.text=item
        holder.itemView.setOnClickListener {
            if (fragment is FragmentList){
                fragment.filterBudget(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return customList.size
    }
}
