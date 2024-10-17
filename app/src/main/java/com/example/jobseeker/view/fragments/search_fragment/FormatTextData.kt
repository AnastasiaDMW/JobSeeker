package com.example.jobseeker.view.fragments.search_fragment

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FormatTextData {

    fun getDeclensePersonWord(count: Int): String {
        val countStr = count.toString()
        return when {
            countStr.endsWith("11") || countStr.endsWith("12") || countStr.endsWith("13") || countStr.endsWith("14") || (count % 100 in 11..19) -> "человек"
            countStr.endsWith("2") || countStr.endsWith("3") || countStr.endsWith("4") -> "человека"
            else -> "человек"
        }
    }

    fun getFormatDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val months = arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")

        return if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = months[calendar.get(Calendar.MONTH)]
            "Опубликовано $day $month"
        } else {
            "Ошибка формата даты"
        }
    }

}