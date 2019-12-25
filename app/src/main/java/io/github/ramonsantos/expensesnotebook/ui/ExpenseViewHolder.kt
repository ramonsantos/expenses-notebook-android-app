package io.github.ramonsantos.expensesnotebook.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.ramonsantos.expensesnotebook.R
import io.github.ramonsantos.expensesnotebook.model.Expense

class ExpenseViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_expense, parent, false)) {
    private var expenseDescriptionTextView: TextView? = null
    private var expenseValueTextView: TextView? = null

    init {
        expenseDescriptionTextView = itemView.findViewById(R.id.item_expense_description)
        expenseValueTextView = itemView.findViewById(R.id.item_expense_value)
    }

    fun bind(expense: Expense) {
        expenseDescriptionTextView?.text = expense.description
        expenseValueTextView?.text = expense.value.toString()
    }
}
