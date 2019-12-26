package io.github.ramonsantos.expensesnotebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "place") val place: String
)
