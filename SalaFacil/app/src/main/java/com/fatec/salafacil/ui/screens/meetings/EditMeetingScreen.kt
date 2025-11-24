package com.fatec.salafacil.ui.screens.meetings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.LinearScale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.components.MeetingTimePickerDialog
import com.fatec.salafacil.ui.components.MeetinngDatePickerDialog
import com.fatec.salafacil.ui.components.OutlinedSecondaryButton
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import com.fatec.salafacil.ui.screens.meetings.formstate.MeetingFormState
import com.fatec.salafacil.ui.screens.meetings.utils.formatLocalTime
import com.fatec.salafacil.ui.screens.meetings.utils.formatMillisToLocalDate
import com.fatec.salafacil.ui.screens.meetings.validators.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMeetingScreen(
    onBackClicked: () -> Unit,
    onSaveButtonClicked: (String, String, LocalDate, LocalTime, LocalTime) -> Unit,
    sala: Sala,
    reuniao: Reuniao
) {
    val currentTime = Calendar.getInstance()

    // Data
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let { data ->
        formatMillisToLocalDate(data)
    } ?: ""

    // Horário de Início
    var showStartTimePicker by remember { mutableStateOf(false) }
    val startTimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    val selectedStartTime: LocalTime? = LocalTime.of(
        startTimePickerState.hour, startTimePickerState.minute
    )

    // Horário de Término
    var showEndTimePicker by remember { mutableStateOf(false) }
    val endTimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    val selectedEndTime: LocalTime? = LocalTime.of(
        endTimePickerState.hour, endTimePickerState.minute
    )

    var formState by remember {
        mutableStateOf(MeetingFormState())
    }

    fun validateForm(): Boolean {
        val tituloError = validateTitulo(formState.titulo)
        val assuntoError = validateAssunto(formState.assunto)
        val dataError = validateData(formState.data)
        val horarioInicioError = validateHorarioNoPassado(formState.data, formState.horarioInicio)
        val horarioTerminoError = validateHorarioNoPassado(formState.data, formState.horarioTermino)
        val intervaloError = validateIntervalo(formState.horarioInicio, formState.horarioTermino)

        formState = formState.copy(
            tituloError = tituloError,
            assuntoError = assuntoError,
            dataError = dataError,
            horarioInicioError = horarioInicioError,
            horarioTerminoError = horarioTerminoError,
            intervaloError = intervaloError
        )

        return (tituloError == null && assuntoError == null && dataError == null && horarioInicioError == null && horarioTerminoError == null && intervaloError == null)
    }

    Scaffold(
        modifier = Modifier.Companion
            .fillMaxSize()
            .padding(10.dp),
        topBar = {
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    modifier = Modifier.Companion.weight(6f),
                    text = PT.edit_meeting_title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )

                IconButton(
                    modifier = Modifier.Companion.weight(1f), onClick = {
                        onBackClicked()
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Retornar"
                    )
                }
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = PT.edit_meeting_room_label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

            Spacer(modifier = Modifier.Companion.height(6.dp))

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Localização",
                    tint = Grey400
                )

                Text(
                    text = sala.endereco,
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey400
                )
            }

            Spacer(modifier = Modifier.Companion.height(12.dp))

            Text(
                text = PT.edit_meeting_info_label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

            Spacer(modifier = Modifier.Companion.height(6.dp))

            // Campo de Título
            OutlinedTextField(
                value = formState.titulo,
                onValueChange = { titulo ->
                    formState = formState.copy(
                        titulo = titulo, tituloError = if (formState.tituloError != null) {
                            validateTitulo(titulo)
                        } else null
                    )
                },
                modifier = Modifier.Companion.fillMaxWidth(),
                label = { Text("Título") },
                isError = formState.tituloError != null,
                supportingText = {
                    formState.tituloError?.let { error ->
                        Text(
                            text = error, color = ErrorColor
                        )
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Brand400
                )
            )

            Spacer(Modifier.Companion.height(16.dp))

            // Campo de Assunto
            OutlinedTextField(
                value = formState.assunto,
                onValueChange = { assunto ->
                    formState = formState.copy(
                        assunto = assunto, assuntoError = if (formState.assuntoError != null) {
                            validateAssunto(assunto)
                        } else null
                    )
                },
                modifier = Modifier.Companion.fillMaxWidth(),
                label = { Text("Assunto") },
                isError = formState.assuntoError != null,
                supportingText = {
                    formState.assuntoError?.let { error ->
                        Text(
                            text = error, color = ErrorColor
                        )
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Brand400
                )
            )

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Selecionar data
            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                modifier = Modifier.Companion.fillMaxWidth(),
                label = { Text("Data") },
                isError = formState.dataError != null,
                supportingText = {
                    formState.dataError?.let { error ->
                        Text(
                            text = error, color = ErrorColor
                        )
                    }
                },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = "Selecionar data"
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Brand400
                )
            )

            if (showDatePicker) {
                MeetinngDatePickerDialog(datePickerState = datePickerState, onDismissRequest = {
                    showDatePicker = false
                }, onDateConfirmed = { date ->
                    formState = formState.copy(data = date)
                })
            }

            // Selecionar horários de começo e término da reunião
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = formatLocalTime(formState.horarioInicio),
                    onValueChange = {},
                    label = { Text("Início") },
                    isError = (formState.horarioInicioError != null) || (formState.intervaloError != null),
                    supportingText = {
                        Column {
                            formState.horarioInicioError?.let {
                                Text(text = it, color = ErrorColor)
                            }

                            formState.intervaloError?.let {
                                Text(text = it, color = ErrorColor)
                            }
                        }
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showStartTimePicker = !showStartTimePicker }) {
                            Icon(
                                imageVector = Icons.Rounded.LinearScale,
                                contentDescription = "Selecionar horário de começo",
                                modifier = Modifier.Companion.rotate(180f)
                            )
                        }
                    },
                    modifier = Modifier.Companion.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brand400
                    )
                )

                Spacer(modifier = Modifier.Companion.width(8.dp))

                OutlinedTextField(
                    value = formatLocalTime(formState.horarioTermino),
                    onValueChange = {},
                    label = { Text("Fim") },
                    isError = (formState.horarioTerminoError != null) || (formState.intervaloError != null),
                    supportingText = {
                        Column {
                            formState.horarioTerminoError?.let {
                                Text(text = it, color = ErrorColor)
                            }

                            formState.intervaloError?.let {
                                Text(text = it, color = ErrorColor)
                            }
                        }
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showEndTimePicker = !showEndTimePicker }) {
                            Icon(
                                imageVector = Icons.Rounded.LinearScale,
                                contentDescription = "Selecionar horário de começo",
                            )
                        }
                    },
                    modifier = Modifier.Companion.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brand400
                    )
                )
            }

            if (showStartTimePicker) {
                MeetingTimePickerDialog(
                    timePickerState = startTimePickerState,
                    onDismissRequest = { showStartTimePicker = false },
                    onTimeSelected = { time ->
                        formState = formState.copy(
                            horarioInicio = time,
                            horarioInicioError = validateHorarioNoPassado(formState.data, time),
                            intervaloError = validateIntervalo(time, formState.horarioTermino)
                        )
                    })
            }

            if (showEndTimePicker) {
                MeetingTimePickerDialog(
                    timePickerState = endTimePickerState,
                    onDismissRequest = { showEndTimePicker = false },
                    onTimeSelected = { time ->
                        formState = formState.copy(
                            horarioTermino = time,
                            horarioTerminoError = validateHorarioNoPassado(formState.data, time),
                            intervaloError = validateIntervalo(formState.horarioInicio, time)
                        )
                    })
            }

            Spacer(modifier = Modifier.Companion.height(64.dp))

            // Confirmar edição
            PrimaryButton(
                modifier = Modifier.Companion.fillMaxWidth(),
                text = PT.edit_save_button,
                onClick = {
                    if (validateForm()) {
                        onSaveButtonClicked(
                            formState.titulo,
                            formState.assunto,
                            formState.data!!,
                            formState.horarioInicio!!,
                            formState.horarioTermino!!
                        )
                    }
                },
                enabled = formState.titulo.isNotBlank() &&
                        formState.assunto.isNotBlank() &&
                        formState.data != null &&
                        formState.horarioInicio != null &&
                        formState.horarioTermino != null
            )

            // Retornar
            OutlinedSecondaryButton(
                modifier = Modifier.Companion.fillMaxWidth(),
                text = PT.meeeting_detials_return_button,
                onClick = { onBackClicked() })
        }
    }
}

@Preview
@Composable
fun EditMeetingScreenPreview() {
    val fakeSala = Sala(
        nome = "Sala 203 - Bloco B",
        endereco = "Av. Paulista, 1500",
        capacidade = 40,
        hasProjector = true,
        hasWhiteboard = true
    )

    val fakeReuniao = Reuniao(
        titulo = "Reunião de Planejamento",
        descricao = "Discussão sobre metas do semestre",
        responsavelId = "user123",
        participantes = listOf("user123", "user456")
    )

    MaterialTheme {
        Surface {
            EditMeetingScreen(
                onBackClicked = {},
                onSaveButtonClicked = { _, _, _, _, _ -> },
                sala = fakeSala,
                reuniao = fakeReuniao
            )
        }
    }
}