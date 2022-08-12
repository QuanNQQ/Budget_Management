package com.example.mock.repository

import androidx.lifecycle.LiveData
import com.example.mock.data.BudgetDao
import com.example.mock.model.Budget
import kotlinx.coroutines.flow.Flow

class BudgetRepository(private val budgetDao : BudgetDao) {
    val readAllData : LiveData<List<Budget>> = budgetDao.readAllData()

    fun readAllDataByType(item : String): Flow<List<Budget>> {
        return budgetDao.readAllDataByType(item)
    }

    suspend fun addBudget(budget : Budget){
        budgetDao.addBudget(budget)
    }

    suspend fun updateBudget(budget: Budget){
        budgetDao.updateBudget(budget)
    }

    suspend fun deleteBudget(budget: Budget){
        budgetDao.deleteBudget(budget)
    }

    suspend fun deleteAllBudget(){
        budgetDao.deleteAllBudget()
    }

    fun getTotalExpense() : Double{
        return budgetDao.getTotalExpense()
    }

    fun getTotalIncome() : Double{
        return budgetDao.getTotalIncome()
    }
}