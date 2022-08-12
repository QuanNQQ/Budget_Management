package com.example.mock.data

import android.content.Context
import androidx.room.*
import com.example.mock.model.Budget

@Database(entities = [Budget :: class], version = 1, exportSchema = false)
abstract class BudgetDatabase : RoomDatabase(){
    abstract fun budgetDao() : BudgetDao
    companion object{
        @Volatile
        private var INSTANCE : BudgetDatabase? = null

        fun getDatabase(context : Context ): BudgetDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    "budget_database")
                    .build()
                INSTANCE = instance
                return instance

            }
        }
    }
}