package com.dev.gka.abda

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun formatDateString(str: String): String {
            val validatedDateString = SimpleDateFormat("d-MMM-yyyy", Locale.US)
            val date = validatedDateString.parse(str)

            val expectedDateFormat = SimpleDateFormat("d MMM, yyyy", Locale.US)
            return expectedDateFormat.format(date!!)
        }
    }
}