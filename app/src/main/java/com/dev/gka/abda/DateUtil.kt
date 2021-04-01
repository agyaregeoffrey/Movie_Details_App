package com.dev.gka.abda

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun formatDateString(date: String): String {
            var formattedDate = date
            val dateFormat = SimpleDateFormat("d, MMM, yyyy", Locale.US)
            formattedDate = dateFormat.format(Date()).toString()

            return formattedDate
        }
    }
}