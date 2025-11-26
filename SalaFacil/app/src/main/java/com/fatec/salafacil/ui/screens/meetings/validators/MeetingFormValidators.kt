package com.fatec.salafacil.ui.screens.meetings.validators

import com.fatec.salafacil.ui.screens.meetings.formstate.MeetingFormState
import com.fatec.salafacil.ui.screens.meetings.utils.timestampToLocalDate
import com.fatec.salafacil.ui.screens.meetings.utils.timestampToLocalTime
import java.time.LocalDate
import java.time.LocalTime

fun validateTitulo(titulo: String): String? {
    return when {
        titulo.isBlank() -> "Título é obrigatório"
        else -> null
    }
}

fun validateData(data: LocalDate?): String? {
    return when {
        data == null -> "Data não pode ser nula"
        data.isBefore(LocalDate.now()) -> "Data não pode ser no passado"
        else -> null
    }
}

fun validateHorarioNoPassado(data: LocalDate?, horario: LocalTime?): String? {
    return when {
        (data == null || horario == null) ->
            "Data e horário não podem ser nulos"

        data.isEqual(LocalDate.now()) && horario.isBefore(LocalTime.now()) ->
            "Horário não pode estar no passado"

        else -> null
    }
}

fun validateIntervalo(horarioInicio: LocalTime?, horarioTermino: LocalTime?): String? {
    if (horarioInicio == null || horarioTermino == null) return null

    return when {
        horarioTermino.isBefore(horarioInicio) ->
            "Horário de término deve ser após o início"

        horarioTermino == horarioInicio ->
            "Os horários não podem ser iguais"

        else -> null
    }
}

fun validateAssunto(assunto: String): String? {
    return when {
        assunto.isBlank() -> "Assunto é obrigatório"
        else -> null
    }
}

fun validateMeetingForm(formState: MeetingFormState): Pair<MeetingFormState, Boolean> {
    val tituloError = validateTitulo(formState.titulo)
    val pautaError = validateAssunto(formState.pauta)
    val dataError = validateData(timestampToLocalDate(formState.data))
    val horarioInicioError = validateHorarioNoPassado(
        timestampToLocalDate(formState.data),
        timestampToLocalTime(formState.horarioInicio)
    )
    val horarioTerminoError = validateHorarioNoPassado(
        timestampToLocalDate(formState.data),
        timestampToLocalTime(formState.horarioTermino)
    )
    val intervaloError = validateIntervalo(
        timestampToLocalTime(formState.horarioInicio),
        timestampToLocalTime(formState.horarioTermino)
    )

    val updatedFormState = formState.copy(
        tituloError = tituloError,
        pautaError = pautaError,
        dataError = dataError,
        horarioInicioError = horarioInicioError,
        horarioTerminoError = horarioTerminoError,
        intervaloError = intervaloError
    )

    val isValid = listOf(
        tituloError,
        pautaError,
        dataError,
        horarioInicioError,
        horarioTerminoError,
        intervaloError
    ).all { it == null }

    return updatedFormState to isValid
}

