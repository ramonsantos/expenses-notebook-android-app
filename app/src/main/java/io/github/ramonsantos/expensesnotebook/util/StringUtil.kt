package io.github.ramonsantos.expensesnotebook.util

class StringUtil {
    companion object {
        fun buildDateString(year: Int, month: Int, day: Int): String {
            val months = arrayOf(
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

            val dayValue = if (day < 10) "0$day" else "$day"
            val monthValue = months[month]

            return "$dayValue/$monthValue/$year"
        }

        fun buildDateStringOnlyNumbers(year: Int, month: Int, day: Int): String {
            val numberMonth = month + 1

            val dayValue = if (day < 10) "0$day" else "$day"
            val monthValue = if (numberMonth < 10) "0$numberMonth" else "$numberMonth"

            return "$dayValue/$monthValue/$year"
        }
    }
}
