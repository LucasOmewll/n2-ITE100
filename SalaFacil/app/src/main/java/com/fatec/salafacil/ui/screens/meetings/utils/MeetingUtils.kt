package com.fatec.salafacil.ui.screens.meetings.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatMillisToLocalDate(millis: Long): String {
    val instant = Instant.ofEpochMilli(millis)
    val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return date.format(formatter)
}

fun formatLocalTime(horario: Timestamp): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    if (horario == null) {
        return ""
    }

    return horario.format(formatter)
}

fun converterMillisParaLocalDate(millis: Long): LocalDate? {
    val instant = Instant.ofEpochMilli(millis)
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Timestamp.toDateString(
    pattern: String = "dd/MM/yyyy",
    locale: Locale = Locale.getDefault()
): String {
    val sdf = SimpleDateFormat(pattern, locale)
    return sdf.format(this.toDate())
}

fun Timestamp.toHourString(
    pattern: String = "HH:mm",
    locale: Locale = Locale.getDefault()
): String {
    val sdf = SimpleDateFormat(pattern, locale)
    return sdf.format(this.toDate())
}

fun timestampToLocalDate(timestamp: Timestamp): LocalDate {
    return timestamp.toDate().toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

// Função para converter Timestamp para LocalTime (se necessário)
fun timestampToLocalTime(timestamp: Timestamp): LocalTime {
    return timestamp.toDate().toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
}

fun formatLocalDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return localDate.format(formatter)
}


fun formatTimestampToDate(timestamp: Timestamp): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(timestamp.toDate())
}

fun formatTimestampToTime(timestamp: Timestamp): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(timestamp.toDate())
}

fun toFirebaseTimestamp(date: LocalDate, time: LocalTime): Timestamp {
    val localDateTime = date.atTime(time)

    val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()

    return Timestamp(Date.from(instant))
}

fun mergeDateAndTime(
    dateTimestamp: Timestamp,
    time: LocalTime
): Timestamp {
    val localDate = timestampToLocalDate(dateTimestamp)

    val localDateTime = localDate.atTime(time)

    val instant = localDateTime
        .atZone(ZoneId.systemDefault())
        .toInstant()

    return Timestamp(Date.from(instant))
}

