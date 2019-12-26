package io.github.ramonsantos.expensesnotebook.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.ramonsantos.expensesnotebook.R
import io.github.ramonsantos.expensesnotebook.model.Expense
import io.github.ramonsantos.expensesnotebook.util.StringUtil
import java.util.*

class ExpenseViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_expense, parent, false)) {
    private var expenseDescriptionTextView: TextView? = null
    private var expenseAmountTextView: TextView? = null
    private var expenseCategoryTextView: TextView? = null
    private var expensePlaceAndDateTextView: TextView? = null

    init {
        expenseDescriptionTextView = itemView.findViewById(R.id.item_expense_description)
        expenseAmountTextView = itemView.findViewById(R.id.item_expense_amount)
        expenseCategoryTextView = itemView.findViewById(R.id.item_expense_category)
        expensePlaceAndDateTextView = itemView.findViewById(R.id.item_expense_place_and_date)
    }

    fun bind(expense: Expense) {
        expenseDescriptionTextView?.text = expense.description
        expenseAmountTextView?.text = "${expense.value} R$"
        expenseCategoryTextView?.text = expense.category
        expensePlaceAndDateTextView?.text = "${expense.place} - ${dateToString(expense.date)}"
    }

    private fun dateToString(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return StringUtil.buildDateString(year, month, day)
    }
}
