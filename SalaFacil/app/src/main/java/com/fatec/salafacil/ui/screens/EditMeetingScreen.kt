package com.fatec.salafacil.ui.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.LinearScale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fatec.salafacil.ui.components.OutlinedSecondaryButton
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Brand100
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.theme.Shapes
import com.fatec.salafacil.ui.translations.PT
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

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

fun converterParaLocalTime() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMeetingScreen(
    onBackClicked: () -> Unit,
    onSaveButtonClicked: (String, String, LocalDate, LocalTime, LocalTime) -> Unit,
    nomeSala: String,
    localizacaoSala: String,
    titulo: String,
    assunto: String,
    data: LocalDate,
    horarioInicio: LocalTime,
    horarioTermino: LocalTime
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
        startTimePickerState.hour,
        startTimePickerState.minute
    )

    // Horário de Término
    var showEndTimePicker by remember { mutableStateOf(false) }
    val endTimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    val selectedEndTime: LocalTime? = LocalTime.of(
        endTimePickerState.hour,
        endTimePickerState.minute
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
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = PT.edit_meeting_title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onBackClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Retornar"
                    )
                }
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = PT.edit_meeting_room_label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Grey500
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Localização",
                    tint = Grey400
                )

                Text(
                    text = localizacaoSala,
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey400
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = PT.edit_meeting_info_label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Grey500
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Campo de Título
            OutlinedTextField(
                value = formState.titulo,
                onValueChange = { titulo ->
                    formState = formState.copy(
                        titulo = titulo,
                        tituloError = if (formState.tituloError != null) {
                            validateTitulo(titulo)
                        } else null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Título") },
                isError = formState.tituloError != null,
                supportingText = {
                    formState.tituloError?.let { error ->
                        Text(
                            text = error,
                            color = ErrorColor
                        )
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Brand400
                )
            )

            Spacer(Modifier.height(16.dp))

            // Campo de Assunto
            OutlinedTextField(
                value = formState.assunto,
                onValueChange = { assunto ->
                    formState = formState.copy(
                        assunto = assunto,
                        assuntoError = if (formState.assuntoError != null) {
                            validateAssunto(assunto)
                        } else null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Assunto") },
                isError = formState.assuntoError != null,
                supportingText = {
                    formState.assuntoError?.let { error ->
                        Text(
                            text = error,
                            color = ErrorColor
                        )
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Brand400
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selecionar data
            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Data") },
                isError = formState.dataError != null,
                supportingText = {
                    formState.dataError?.let { error ->
                        Text(
                            text = error,
                            color = ErrorColor
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
                Dialog(onDismissRequest = { showDatePicker = false }) {
                    Surface(
                        shape = Shapes.medium,
                        tonalElevation = 4.dp,
                        modifier = Modifier
                            .requiredWidth(380.dp)
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            DatePicker(
                                modifier = Modifier
                                    .shadow(elevation = 4.dp),
                                state = datePickerState,
                                showModeToggle = false,
                                colors = DatePickerDefaults.colors(
                                    todayContentColor = Brand500,
                                    todayDateBorderColor = Brand500,
                                    selectedDayContainerColor = Brand400,
                                    selectedYearContainerColor = Brand400
                                )
                            )
                        }
                    }
                }
            }

            // Selecionar horários de começo e término da reunião
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = formatLocalTime(selectedStartTime),
                    onValueChange = {},
                    label = { Text("Início") },
                    isError = formState.horarioInicioError != null,
                    supportingText = {
                        formState.horarioInicioError?.let { error ->
                            Text(
                                text = error,
                                color = ErrorColor
                            )
                        }
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showStartTimePicker = !showStartTimePicker }) {
                            Icon(
                                imageVector = Icons.Rounded.LinearScale,
                                contentDescription = "Selecionar horário de começo",
                                modifier = Modifier.rotate(180f)
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brand400
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = formatLocalTime(selectedEndTime),
                    onValueChange = {},
                    label = { Text("Fim") },
                    isError = formState.horarioTerminoError != null,
                    supportingText = {
                        formState.horarioTerminoError?.let { error ->
                            Text(
                                text = error,
                                color = ErrorColor
                            )
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
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brand400
                    )
                )
            }

            if (showStartTimePicker) {
                Dialog(onDismissRequest = { showStartTimePicker = false }) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .padding(16.dp)
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface, shape = Shapes.medium),
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = PT.popup_horario,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Grey400
                                )
                            }

                            Spacer(Modifier.height(16.dp))

                            TimeInput(
                                state = startTimePickerState,
                                colors = TimePickerDefaults.colors(
                                    timeSelectorSelectedContentColor = Brand500,
                                    timeSelectorSelectedContainerColor = Brand100,
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedSecondaryButton(
                                    text = "Cancelar",
                                    onClick = {
                                        showStartTimePicker = false
                                    }
                                )

                                Spacer(Modifier.width(6.dp))

                                PrimaryButton(
                                    text = "Confirmar",
                                    onClick = {
                                        val selectedTime = LocalTime.of(
                                            startTimePickerState.hour,
                                            startTimePickerState.minute
                                        )

                                        formState = formState.copy(horarioInicio = selectedTime)

                                        showStartTimePicker = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (showEndTimePicker) {
                Dialog(
                    onDismissRequest = { showEndTimePicker = false }
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .padding(16.dp)
                            .shadow(elevation = 4.dp)
                            .background(MaterialTheme.colorScheme.surface, shape = Shapes.medium),
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = PT.popup_horario,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Grey400
                                )
                            }

                            Spacer(Modifier.height(16.dp))

                            TimeInput(
                                state = endTimePickerState,
                                colors = TimePickerDefaults.colors(
                                    timeSelectorSelectedContentColor = Brand500,
                                    timeSelectorSelectedContainerColor = Brand100,
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedSecondaryButton(
                                    text = "Cancelar",
                                    onClick = {
                                        showEndTimePicker = false
                                    }
                                )

                                Spacer(Modifier.width(6.dp))

                                PrimaryButton(
                                    text = "Confirmar",
                                    onClick = {
                                        val selectedTime = LocalTime.of(
                                            endTimePickerState.hour,
                                            endTimePickerState.minute
                                        )

                                        formState = formState.copy(horarioTermino = selectedTime)

                                        showEndTimePicker = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))
            // Confirmar edição
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = PT.edit_save_button,
                onClick = { onSaveButtonClicked },
                enabled = formState.titulo.isNotBlank() && formState.assunto.isNotBlank() && formState.data != null && formState.horarioInicio != null && formState.horarioTermino != null
            )

            // Retornar
            OutlinedSecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = PT.meeeting_detials_return_button,
                onClick = { onBackClicked() }
            )
        }


    }
}

@Preview
@Composable
fun EditMeetingScreenPreview() {
    MaterialTheme {
        Surface() {
            EditMeetingScreen(
                onBackClicked = {},
                onSaveButtonClicked = { _, _, _, _, _ -> },
                nomeSala = "Sala 12 - Informática",
                localizacaoSala = "Prédio A - 2º andar",
                titulo = "Reunião de Projeto",
                assunto = "Planejamento do Sprint",
                data = LocalDate.now(),
                horarioInicio = LocalTime.of(14, 0),
                horarioTermino = LocalTime.of(15, 0)
            )
        }
    }
}