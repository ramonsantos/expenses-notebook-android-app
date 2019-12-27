package io.github.ramonsantos.expensesnotebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "place") val place: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "is_sent") var isSent: Long = 0
) {
    companion object {
        const val TO_SEND_STATUS: Long = 0
        const val SENT_STATUS: Long = 1
    }
}
