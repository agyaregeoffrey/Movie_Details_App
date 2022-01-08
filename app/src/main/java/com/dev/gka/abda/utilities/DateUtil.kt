package com.dev.gka.abda.utilities

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun formatDateString(str: String?): String {
            return if (str != null && str != "") {
                val format = SimpleDateFormat("yyyy-MM-d", Locale.US)
                val date = format.parse(str)

                val expectedDateFormat = SimpleDateFormat("d MMM, yyyy", Locale.US)
                expectedDateFormat.format(date!!)
            } else {
                "Date not available"
            }
        }
    }
}