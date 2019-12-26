package io.github.ramonsantos.expensesnotebook.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.github.ramonsantos.expensesnotebook.R
import io.github.ramonsantos.expensesnotebook.config.AppDatabase
import io.github.ramonsantos.expensesnotebook.model.Expense
import io.github.ramonsantos.expensesnotebook.util.Mask
import io.github.ramonsantos.expensesnotebook.util.StringUtil
import kotlinx.android.synthetic.main.activity_expense_form.*
import java.util.*

class ExpenseFormActivity : AppCompatActivity() {
    private lateinit var appDatabase: AppDatabase
    private lateinit var placeSpinner: Spinner
    private lateinit var categoriesSpinner: Spinner
    private lateinit var dateValues: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_form)

        buildComponents()

        appDatabase = AppDatabase.getInstance(applicationContext)
    }

    fun saveExpense(view: View) {
        if (!validateForm()) {
            return
        }

        appDatabase.expenseDao().create(buildExpense())

        Toast.makeText(this, R.string.message_expense_created, Toast.LENGTH_SHORT).show()

        super.finish()
    }

    private fun validateForm(): Boolean {
        if (description_edit_text.text.toString().isBlank()) {
            showValidatorAlert(getString(R.string.label_description))
        } else if (amount_edit_text.text.toString().isBlank() || amount_edit_text.text.toString() == "0,00") {
            showValidatorAlert(getString(R.string.label_amount))
        } else {
            return true
        }

        return false
    }

    private fun showValidatorAlert(field: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.message_warning))
        alertDialog.setMessage("${getString(R.string.message_the_field)} \"$field\" ${getString(R.string.message_is_mandatory)}")
        alertDialog.setPositiveButton(getString(R.string.message_ok), { _, _ -> })
        alertDialog.show()
    }

    private fun buildComponents() {
        buildCategorySpinner()
        buildPlaceSpinner()
        buildDatePicker()

        amount_edit_text.addTextChangedListener(Mask.moneyMask(amount_edit_text))
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

    private fun buildDatePicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        dateValues = arrayOf(currentYear, currentMonth, currentDay)

        date_value_text_view.text =
            StringUtil.buildDateString(currentYear, currentMonth, currentDay)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                date_value_text_view.text =
                    StringUtil.buildDateString(year, monthOfYear, dayOfMonth)
                dateValues = arrayOf(year, monthOfYear, dayOfMonth)
            },
            currentYear,
            currentMonth,
            currentDay
        )

        pick_date_button.setOnClickListener { dpd.show() }
        date_value_text_view.setOnClickListener { dpd.show() }
    }

    private fun buildExpense(): Expense {
        val description: String = description_edit_text.text.toString()
        val amount: Double =
            amount_edit_text.text.toString().replace(".", "").replace(",", ".").toDouble()
        val category: String = categoriesSpinner.selectedItem.toString()
        val place: String = placeSpinner.selectedItem.toString()


        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, dateValues[0])
        calendar.set(Calendar.MONTH, dateValues[1])
        calendar.set(Calendar.DAY_OF_MONTH, dateValues[2])

        return Expense(0, description, amount, category, place, calendar.time)
    }
}
