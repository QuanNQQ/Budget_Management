package com.example.mock.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mock.R
import com.example.mock.const.*
import com.example.mock.fragment.FragmentListDirections
import com.example.mock.model.Budget
import java.lang.Math.abs

class BudgetAdapter : RecyclerView.Adapter<BudgetAdapter.MyViewHolder>() {
    private var budgetList = emptyList<Budget>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val trans : TextView = itemView.findViewById(R.id.tv_trans)
        val label: TextView = itemView.findViewById(R.id.tv_label)
        val amount: TextView = itemView.findViewById(R.id.tv_amount)
        val date : TextView = itemView.findViewById(R.id.tv_date)

        val rowLayout : CardView = itemView.findViewById(R.id.row_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = budgetList[position]
        val context : Context = holder.amount.context

        if(currentItem.type == INCOME){
            holder.amount.text = "+ %.1f$".format(currentItem.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.green))
            holder.trans.text = currentItem.type
            holder.trans.setTextColor(ContextCompat.getColor(context, R.color.green))
        }
        else {
            holder.amount.text = "- %.1f$".format(abs(currentItem.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
            holder.trans.text = currentItem.type
            holder.trans.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        holder.label.text = currentItem.label
        holder.date.text = currentItem.datetime


        holder.rowLayout.setOnClickListener{
            val action = FragmentListDirections.actionFragmentListToFragmentUpdate(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    fun setData(user : List<Budget>){
        this.budgetList = user
        notifyDataSetChanged()
    }
}