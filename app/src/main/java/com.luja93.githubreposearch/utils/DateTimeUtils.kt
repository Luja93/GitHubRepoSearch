package com.luja93.githubreposearch.utils

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class DateTimeUtils {

    companion object {
        private const val API_DATE_FORMAT = "dd MMM yyyy 'at' HH:mm"
    }

    fun getRepoDateString(date: String): String {
        val formatter = DateTimeFormatter
            .ofPattern(API_DATE_FORMAT, Locale.getDefault())

        return getLocalFormattedTime(date, formatter)
    }

    private fun getLocalFormattedTime(date: String, formatter: DateTimeFormatter): String {
        return ZonedDateTime.parse(date).toOffsetDateTime()
            .atZoneSameInstant(ZoneId.systemDefault()).format(formatter)
    }

}