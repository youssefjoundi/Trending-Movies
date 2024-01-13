package com.yassir.trendingmovies.ui.Moviesholder

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getYearFromDate(dateString: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = dateFormat.parse(dateString)
    val calendar = Calendar.getInstance()
    if (date != null) {
        calendar.time = date
    }
    return calendar.get(Calendar.YEAR).toString()
}