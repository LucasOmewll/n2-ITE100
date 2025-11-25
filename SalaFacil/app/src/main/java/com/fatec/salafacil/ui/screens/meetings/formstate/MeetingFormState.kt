package com.fatec.salafacil.ui.screens.meetings.formstate

import java.time.LocalDate
import java.time.LocalTime

data class MeetingFormState(
    val titulo: String = "",
    val tituloError: String? = null,

    val assunto: String = "",
    val assuntoError: String? = null,

    val data: LocalDate? = null,
    val dataError: String? = null,

    val horarioInicio: LocalTime? = null,
    val horarioInicioError: String? = null,

    val horarioTermino: LocalTime? = null,
    val horarioTerminoError: String? = null,
    val intervaloError: String? = null
)

