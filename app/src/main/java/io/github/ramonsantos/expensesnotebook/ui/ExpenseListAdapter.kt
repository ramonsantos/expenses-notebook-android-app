package io.github.ramonsantos.expensesnotebook.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ramonsantos.expensesnotebook.model.Expense

class ExpenseListAdapter(private val list: List<Expense>) :
    RecyclerView.Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExpenseViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense: Expense = list[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int = list.size
}
