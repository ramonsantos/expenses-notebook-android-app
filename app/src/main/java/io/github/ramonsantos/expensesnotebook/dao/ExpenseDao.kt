package io.github.ramonsantos.expensesnotebook.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.ramonsantos.expensesnotebook.model.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense WHERE is_sent = 0 ORDER BY date")
    fun getAll(): List<Expense>

    @Insert
    fun create(vararg expense: Expense)

    @Update
    fun update(expense: Expense)
}
