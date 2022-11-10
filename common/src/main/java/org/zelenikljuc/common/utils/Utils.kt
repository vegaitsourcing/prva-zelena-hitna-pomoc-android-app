package org.zelenikljuc.common.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
    SimpleDateFormat("dd.MM.yyyy").let { formatter ->
        TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
    }

inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        onSdk29()
    } else null
}