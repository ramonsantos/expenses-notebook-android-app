package io.github.ramonsantos.expensesnotebook.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import io.github.ramonsantos.expensesnotebook.R
import io.github.ramonsantos.expensesnotebook.config.AppDatabase
import io.github.ramonsantos.expensesnotebook.dao.ExpenseDao
import io.github.ramonsantos.expensesnotebook.model.Expense
import io.github.ramonsantos.expensesnotebook.util.StringUtil
import java.util.*

class ExportExpensesByEmailIntentService : IntentService("ExportExpensesByEmailIntentService") {
    private lateinit var appDatabase: AppDatabase
    private lateinit var expenseDao: ExpenseDao
    private lateinit var sharedPref: SharedPreferences

    override fun onHandleIntent(intent: Intent?) {
        appDatabase = AppDatabase.getInstance(applicationContext)
        expenseDao = appDatabase.expenseDao()
        var expenses = expenseDao.getAll()


        try {
            sendEmail(
                getEmailToSend(),
                applicationContext.getString(R.string.message_report_expense_subject),
                buildCSV(expenses)
            )
            updateExpensesSentStatus(expenses, Expense.SENT_STATUS)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            updateExpensesSentStatus(expenses, Expense.TO_SEND_STATUS)
        }
    }

    private fun updateExpensesSentStatus(expenses: List<Expense>, sentStatus: Long) {
        expenses.forEach {
            it.isSent = sentStatus
            expenseDao.update(it)
        }
    }

    private fun getEmailToSend(): String {
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_send_to_email), Context.MODE_PRIVATE
        )

        return sharedPref.getString(
            applicationContext.getString(R.string.preference_send_to_email_key),
            ""
        ).toString()
    }

    private fun buildCSV(expenses: List<Expense>): String {
        var csvContent = StringBuilder("")

        expenses.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = it.date

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            csvContent.append(
                "${it.description};${it.amount};${StringUtil.buildDateStringOnlyNumbers(
                    year,
                    month,
                    day
                )};${it.category};${it.place}\n"
            )
        }

        return csvContent.toString()
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)
        mIntent.setPackage("com.google.android.gm")

        startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
    }
}
