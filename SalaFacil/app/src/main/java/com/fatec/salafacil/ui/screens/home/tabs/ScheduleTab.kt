package com.fatec.salafacil.ui.screens.home.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.components.NotImplementedCard
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

// Aba de Agenda
@Composable
fun ScheduleTab() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = PT.schedule_tab_label,
                style = MaterialTheme.typography.bodyMedium,
                color = Grey500
            )
        }

        // Calendário
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            NotImplementedCard {

            }

        }
    }
}

@Preview
@Composable
fun ScheduleTabPreview() {
    MaterialTheme {
        Surface {
            ScheduleTab()
        }
    }
}