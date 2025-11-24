package com.fatec.salafacil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fatec.salafacil.ui.theme.Brand100
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Shapes
import com.fatec.salafacil.ui.translations.PT
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingTimePickerDialog(
    timePickerState: TimePickerState,
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier.Companion
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(16.dp)
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colorScheme.surface, shape = Shapes.medium),
        ) {
            Column(
                modifier = Modifier.Companion
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = PT.popup_horario,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Grey400
                    )
                }

                Spacer(Modifier.Companion.height(16.dp))

                TimeInput(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        timeSelectorSelectedContentColor = Brand500,
                        timeSelectorSelectedContainerColor = Brand100,
                    )
                )
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedSecondaryButton(
                        text = "Cancelar",
                        onClick = {
                            onDismissRequest()
                        }
                    )

                    Spacer(Modifier.Companion.width(6.dp))

                    PrimaryButton(
                        text = "Confirmar",
                        onClick = {
                            val selectedTime = LocalTime.of(
                                timePickerState.hour,
                                timePickerState.minute
                            )

                            onTimeSelected(selectedTime)
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}