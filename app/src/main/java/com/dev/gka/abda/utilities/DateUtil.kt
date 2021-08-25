package com.dev.gka.abda.utilities

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun formatDateString(str: String): String {
            val format = SimpleDateFormat("yyyy-MM-d", Locale.US)
            val date = format.parse(str)

            val expectedDateFormat = SimpleDateFormat("d MMM, yyyy", Locale.US)
            return expectedDateFormat.format(date!!)
        }
    }
}