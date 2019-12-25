package io.github.ramonsantos.expensesnotebook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import io.github.ramonsantos.expensesnotebook.R
import io.github.ramonsantos.expensesnotebook.config.AppDatabase
import io.github.ramonsantos.expensesnotebook.model.Expense
import kotlinx.android.synthetic.main.activity_expense_form.*

class ExpenseFormActivity : AppCompatActivity() {
    private lateinit var appDatabase: AppDatabase
    private lateinit var placeSpinner: Spinner
    private lateinit var categoriesSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_form)

        buildComponents()

        appDatabase = AppDatabase.getInstance(applicationContext)
    }

    fun saveExpense(view : View) {
        appDatabase.expenseDao().create(buildExpense())

        Toast.makeText(this, R.string.message_expense_created, Toast.LENGTH_SHORT).show()

        super.finish()
    }

    private fun buildComponents() {
        buildCategorySpinner()
        buildPlaceSpinner()
    }

    private fun buildCategorySpinner() {
        categoriesSpinner = category_spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoriesSpinner.adapter = adapter
        }
    }

    private fun buildPlaceSpinner() {
        placeSpinner = place_spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            placeSpinner.adapter = adapter
        }
    }

    private fun buildExpense(): Expense {
        val description : String = description_edit_text.text.toString()
        val amount : Double = amount_edit_text.text.toString().toDouble()
        val category : String = categoriesSpinner.selectedItem.toString()
        val date: String = date_edit_text.text.toString()
        val place : String = placeSpinner.selectedItem.toString()

        return Expense(0, description, amount, category, place, "")
    }
}
