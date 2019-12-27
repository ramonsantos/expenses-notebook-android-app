package io.github.ramonsantos.expensesnotebook.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.ramonsantos.expensesnotebook.model.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense ORDER BY date LIMIT 50")
    fun getAll(): List<Expense>

    @Insert
    fun create(vararg expense: Expense)

    @Delete
    fun delete(expense: Expense)
}
