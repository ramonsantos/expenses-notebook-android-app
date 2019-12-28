package io.github.ramonsantos.expensesnotebook.ui

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import io.github.ramonsantos.expensesnotebook.model.Expense

class ExpenseListAdapter(private val list: List<Expense>) :
    RecyclerView.Adapter<ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ExpenseViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        if (position + 1 == itemCount) {
            setBottomMargin(
                holder.itemView,
                (Resources.getSystem().displayMetrics.density * 72).toInt()
            )
        } else {
            setBottomMargin(holder.itemView, 0)
        }

        val expense: Expense = list[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int = list.size

    companion object {
        private fun setBottomMargin(view: View, bottomMargin: Int) {
            if (view.layoutParams is MarginLayoutParams) {
                val params = view.layoutParams as MarginLayoutParams
                params.setMargins(
                    params.leftMargin,
                    params.topMargin,
                    params.rightMargin,
                    bottomMargin
                )
                view.requestLayout()
            }
        }
    }
}
