package com.fatec.salafacil.ui.screens.meetings.formstate

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.LocalTime

data class MeetingFormState(
    val titulo: String = "",
    val tituloError: String? = null,

    val pauta: String = "",
    val pautaError: String? = null,

    val data: Timestamp = Timestamp.now(),
    val dataError: String? = null,

    val horarioInicio: Timestamp = Timestamp.now(),
    val horarioInicioError: String? = null,

    val horarioTermino: Timestamp = Timestamp.now(),
    val horarioTerminoError: String? = null,
    val intervaloError: String? = null
)

