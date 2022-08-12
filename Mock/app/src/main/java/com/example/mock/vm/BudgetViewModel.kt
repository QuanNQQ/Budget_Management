package com.example.mock.vm

import android.app.Application
import androidx.lifecycle.*
import com.example.mock.data.BudgetDatabase
import com.example.mock.model.Budget
import com.example.mock.repository.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BudgetViewModel(application : Application) : AndroidViewModel(application){

    val readAllData: LiveData<List<Budget>>

    private val repository : BudgetRepository

    val income : MutableLiveData<Double> = MutableLiveData()
    val expense : MutableLiveData<Double> = MutableLiveData()



    init {
        val budgetDao = BudgetDatabase.getDatabase(
            application
        ).budgetDao()
        repository = BudgetRepository(budgetDao)
        readAllData = repository.readAllData
    }



    // add Budget
    fun addBudget(budget : Budget){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBudget(budget)

        }
    }

    //update Budget
    fun updateBudget(budget: Budget){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBudget(budget)
        }
    }

    //DeleteBudget
    fun deleteBudget(budget: Budget){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteBudget(budget)
        }
    }

    //delete All Budget
    fun deleteAllBudget(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllBudget()
        }
    }

    //get Total Amount By TyPe
    fun getTotalAmountByType() {
        viewModelScope.launch(Dispatchers.IO) {
            income.postValue(repository.getTotalIncome())
            expense.postValue(repository.getTotalExpense())
        }
    }

    //read all data by type
    fun readAllDataByType(item : String) : LiveData<List<Budget>>{
        return  repository.readAllDataByType(item).asLiveData()
    }

}