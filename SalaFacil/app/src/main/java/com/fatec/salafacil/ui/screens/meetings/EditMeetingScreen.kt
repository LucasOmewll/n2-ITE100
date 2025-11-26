package com.fatec.salafacil.ui.screens.meetings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.components.MeetingForm
import com.fatec.salafacil.ui.components.OutlinedSecondaryButton
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import com.fatec.salafacil.ui.screens.meetings.formstate.MeetingFormState
import com.fatec.salafacil.ui.screens.meetings.utils.timestampToLocalDate
import com.fatec.salafacil.ui.screens.meetings.utils.timestampToLocalTime
import com.fatec.salafacil.ui.screens.meetings.validators.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMeetingScreen(
    onBackClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    sala: Sala,
    reuniao: Reuniao
) {
    val currentTime = Calendar.getInstance()

    val datePickerState = rememberDatePickerState()

    var formState by remember {
        mutableStateOf(
            MeetingFormState(
                titulo = reuniao.titulo,
                pauta = reuniao.pauta,
                data = reuniao.data,
                horarioInicio = reuniao.dataHoraInicio,
                horarioTermino = reuniao.dataHoraTermino
            )
        )
    }

    fun validateForm(): Boolean {
        val tituloError = validateTitulo(formState.titulo)
        val pautaError = validateAssunto(formState.pauta)
        val dataError = validateData(timestampToLocalDate(formState.data))
        val horarioInicioError = validateHorarioNoPassado(timestampToLocalDate(formState.data), timestampToLocalTime(formState.horarioInicio))
        val horarioTerminoError = validateHorarioNoPassado(timestampToLocalDate(formState.data), timestampToLocalTime(formState.horarioTermino))
        val intervaloError = validateIntervalo(timestampToLocalTime(formState.horarioInicio), timestampToLocalTime(formState.horarioTermino))

        formState = formState.copy(
            tituloError = tituloError,
            pautaError = pautaError,
            dataError = dataError,
            horarioInicioError = horarioInicioError,
            horarioTerminoError = horarioTerminoError,
            intervaloError = intervaloError
        )

        return (tituloError == null && pautaError == null && dataError == null && horarioInicioError == null && horarioTerminoError == null && intervaloError == null)
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
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, end = 10.dp, top = 10.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Confirmar edição
                PrimaryButton(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    text = PT.edit_save_button,
                    onClick = {
                        if (validateForm()) {
                            onSaveButtonClicked()
                        }
                    },
                    enabled = formState.titulo.isNotBlank() &&
                            formState.pauta.isNotBlank() &&
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

            MeetingForm(
                formState = formState,
                onFormChange = {updated ->
                    formState = updated
                }
            )
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

    MaterialTheme {
        Surface {

        }
    }
}