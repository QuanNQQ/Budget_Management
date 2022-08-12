package com.example.mock.const

object Constants {
    fun transactionType():ArrayList<String>{
        val list= ArrayList<String>()
        list.add("Income")
        list.add("Expense")
        return list
    }
}