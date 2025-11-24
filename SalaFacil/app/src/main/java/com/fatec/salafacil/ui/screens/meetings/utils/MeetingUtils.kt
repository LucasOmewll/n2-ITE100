package com.fatec.salafacil.ui.screens.meetings.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatMillisToLocalDate(millis: Long): String {
    val instant = Instant.ofEpochMilli(millis)
    val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return date.format(formatter)
}

fun formatLocalTime(horario: LocalTime?): String {
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