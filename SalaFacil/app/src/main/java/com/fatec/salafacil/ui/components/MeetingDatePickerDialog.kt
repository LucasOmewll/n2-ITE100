package com.fatec.salafacil.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fatec.salafacil.ui.screens.meetings.utils.converterMillisParaLocalDate
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.Shapes
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetinngDatePickerDialog(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateConfirmed: (LocalDate?) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            shape = Shapes.medium,
            tonalElevation = 4.dp,
            modifier = Modifier.Companion
                .requiredWidth(380.dp)
        ) {
            Column(Modifier.Companion.fillMaxWidth()) {

                DatePicker(
                    modifier = Modifier.Companion
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

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            onDateConfirmed(converterMillisParaLocalDate(millis))
                        }
                        onDismissRequest()
                    }
                ) {
                    Text("Selecionar")
                }
            }
        }
    }
}