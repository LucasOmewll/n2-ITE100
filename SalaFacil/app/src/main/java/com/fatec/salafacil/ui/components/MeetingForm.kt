package com.fatec.salafacil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.rounded.LinearScale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.screens.meetings.formstate.MeetingFormState
import com.fatec.salafacil.ui.screens.meetings.utils.formatLocalTime
import com.fatec.salafacil.ui.screens.meetings.utils.formatMillisToLocalDate
import com.fatec.salafacil.ui.screens.meetings.utils.mergeDateAndTime
import com.fatec.salafacil.ui.screens.meetings.utils.timestampToLocalDate
import com.fatec.salafacil.ui.screens.meetings.utils.timestampToLocalTime
import com.fatec.salafacil.ui.screens.meetings.utils.toFirebaseTimestamp
import com.fatec.salafacil.ui.screens.meetings.validators.validateAssunto
import com.fatec.salafacil.ui.screens.meetings.validators.validateData
import com.fatec.salafacil.ui.screens.meetings.validators.validateHorarioNoPassado
import com.fatec.salafacil.ui.screens.meetings.validators.validateIntervalo
import com.fatec.salafacil.ui.screens.meetings.validators.validateTitulo
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT
import com.google.firebase.Timestamp
import java.time.ZoneId
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingForm(
    formState: MeetingFormState,
    onFormChange: (MeetingFormState) -> Unit
) {
    // Estado dos pickers
    val currentTime = Calendar.getInstance()

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let { data ->
        formatMillisToLocalDate(data)
    } ?: ""

    var showStartTimePicker by remember { mutableStateOf(false) }
    val startTimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    var showEndTimePicker by remember { mutableStateOf(false) }
    val endTimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    Column(
        verticalArrangement = Arrangement.Top
    ) {
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
            onValueChange = { new ->
                onFormChange(
                    formState.copy(
                        titulo = new,
                        tituloError = validateTitulo(new)
                    )
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
            trailingIcon = { Icon(
                imageVector = Icons.Outlined.Title,
                contentDescription = "Assunto"
            ) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Brand400,
                focusedBorderColor = Brand400
            )
        )

        Spacer(Modifier.Companion.height(16.dp))

        // Campo de Assunto
        OutlinedTextField(
            value = formState.pauta,
            onValueChange = { new ->
                onFormChange(
                    formState.copy(
                        pauta = new,
                        pautaError = validateAssunto(new)
                    )
                )
            },
            modifier = Modifier.Companion.fillMaxWidth(),
            label = { Text("Assunto") },
            isError = formState.pautaError != null,
            supportingText = {
                formState.pautaError?.let { error ->
                    Text(
                        text = error, color = ErrorColor
                    )
                }
            },
            trailingIcon = { Icon(
                imageVector = Icons.Outlined.Tag,
                contentDescription = "Assunto"
            ) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Brand400,
                focusedBorderColor = Brand400
            )
        )

        Spacer(modifier = Modifier.Companion.height(16.dp))

        // Selecionar data
        OutlinedTextField(
            value = formState.data?.let { timestampToLocalDate(it).toString() } ?: "",
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
                focusedLabelColor = Brand400,
                focusedBorderColor = Brand400
            )
        )

        if (showDatePicker) {
            MeetinngDatePickerDialog(datePickerState = datePickerState, onDismissRequest = {
                showDatePicker = false
            }, onDateConfirmed = { date ->
                val instant = date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()

                onFormChange(
                    formState.copy(
                        data = Timestamp(java.util.Date.from(instant)),
                        dataError = validateData(date)
                    )
                )
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

            // Picker início
            if (showStartTimePicker) {
                MeetingTimePickerDialog(
                    timePickerState = startTimePickerState,
                    onDismissRequest = { showStartTimePicker = false },
                    onTimeSelected = { time ->

                        val timestamp = mergeDateAndTime(formState.data, time)

                        onFormChange(
                            formState.copy(
                                horarioInicio = timestamp,
                                horarioInicioError = validateHorarioNoPassado(timestampToLocalDate(formState.data), time),
                                intervaloError = validateIntervalo(time, timestampToLocalTime(formState.horarioTermino))
                            )
                        )
                    })
            }

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

        if (showEndTimePicker) {
            MeetingTimePickerDialog(
                timePickerState = endTimePickerState,
                onDismissRequest = { showEndTimePicker = false },
                onTimeSelected = { time ->
                    val timestamp = mergeDateAndTime(formState.data, time)

                    onFormChange(
                        formState.copy(
                            horarioTermino = timestamp,
                            horarioTerminoError = validateHorarioNoPassado(timestampToLocalDate(formState.data), time),
                            intervaloError = validateIntervalo(time, timestampToLocalTime(formState.horarioTermino))
                        )
                    )
                })
        }
    }
}

@Preview
@Composable
fun MeetingFormPreview() {
    MaterialTheme {
        Surface {
            MeetingForm(
                formState = MeetingFormState(),
                onFormChange = {}
            )
        }
    }
}