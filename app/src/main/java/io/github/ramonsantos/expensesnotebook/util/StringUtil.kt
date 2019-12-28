package io.github.ramonsantos.expensesnotebook.util

import android.icu.text.NumberFormat
import java.util.*

class StringUtil {
    companion object {
        private val MONTHS = arrayOf(
            "Jan",
            "Fev",
            "Mar",
            "Abr",
            "Mai",
            "Jun",
            "Jul",
            "Ago",
            "Set",
            "Out",
            "Nov",
            "Dez"
        )

        fun dateToString(date: Date): String {
            val calendar = Calendar.getInstance()
            calendar.time = date

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return StringUtil.buildDateString(year, month, day)
        }

        fun buildDateString(year: Int, month: Int, day: Int): String {
            val dayValue = if (day < 10) "0$day" else "$day"
            val monthValue = MONTHS[month]

            return "$dayValue/$monthValue/$year"
        }

        fun buildDateStringOnlyNumbers(year: Int, month: Int, day: Int): String {
            val numberMonth = month + 1

            val dayValue = if (day < 10) "0$day" else "$day"
            val monthValue = if (numberMonth < 10) "0$numberMonth" else "$numberMonth"

            return "$dayValue/$monthValue/$year"
        }

        fun amountToCurrencyFormat(amount: Double): String {
            return NumberFormat.getCurrencyInstance().format(amount).replace("R", "")
                .replace("$", "")
        }

        fun isValidEmail(email: String): Boolean {
            return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}
