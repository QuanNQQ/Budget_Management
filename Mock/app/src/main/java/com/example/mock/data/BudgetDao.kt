package com.example.mock.data
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mock.model.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBudget(budget : Budget)

    @Update
    suspend fun updateBudget(user: Budget)

    @Delete
    suspend fun deleteBudget(user: Budget)

    @Query("Delete from budget_table")
    suspend fun deleteAllBudget()

    @Query("Select * from budget_table order by id ASC")
    fun readAllData(): LiveData<List<Budget>>


    @Query("Select * from budget_table where type = :item")
    fun readAllDataByType(item : String): Flow<List<Budget>>

    @Query("Select SUM(amount) from budget_table where type = 'Expense'")
    fun getTotalExpense() : Double

    @Query("Select Sum(amount) from budget_table where type = 'Income'")
    fun getTotalIncome() : Double

}