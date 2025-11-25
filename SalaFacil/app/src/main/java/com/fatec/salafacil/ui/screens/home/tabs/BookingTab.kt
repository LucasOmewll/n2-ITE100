package com.fatec.salafacil.ui.screens.home.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.components.ExpandableRoomCard
import com.fatec.salafacil.ui.theme.Grey300
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

// Tela de Reservar Sala
@Composable
fun BookingTab(
    salas: List<Sala>? = null,
    onViewAllClick: () -> Unit
) {
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
                text = PT.booking_tab_label,
                style = MaterialTheme.typography.bodyMedium,
                color = Grey500
            )
            Text(
                text = PT.booking_tab_clickable,
                style = MaterialTheme.typography.bodyMedium,
                color = Grey300,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onViewAllClick }
            )
        }

        // Lista de Salas
        if (salas != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(
                    items = salas,
                    key = { sala -> sala.id }
                ) { sala ->
                    ExpandableRoomCard(
                        sala = sala,
                        primaryButtonAction = { /* action */ }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "Salas indisponíveis",
                    tint = Grey400
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = PT.no_rooms_avaliable,
                    style = MaterialTheme.typography.labelLarge,
                    color = Grey400
                )
            }
        }
    }
}

@Preview
@Composable
fun BookingTabPreview() {
    val salas = listOf(
        Sala(
            id = "1",
            nome = "Sala de Reuniões A",
            endereco = "Bloco A - 1º Andar",
            capacidade = 10,
            imageUrl = "https://example.com/sala1.jpg",
            hasProjector = true,
            hasWhiteboard = true,
            hasAirConditioning = true,
            hasWifi = true,
            hasVideoConference = false
        ),
        Sala(
            id = "2",
            nome = "Auditório Principal",
            endereco = "Bloco Central - Térreo",
            capacidade = 50,
            imageUrl = "https://example.com/auditorio.jpg",
            hasProjector = true,
            hasWhiteboard = false,
            hasAirConditioning = true,
            hasWifi = true,
            hasVideoConference = true
        ),
        Sala(
            id = "3",
            nome = "Sala de Treinamento B",
            endereco = "Bloco B - 2º Andar",
            capacidade = 25,
            imageUrl = "",
            hasProjector = true,
            hasWhiteboard = true,
            hasAirConditioning = true,
            hasWifi = true,
            hasVideoConference = true
        ),
        Sala(
            id = "4",
            nome = "Sala de Conferências",
            endereco = "Bloco Administrativo - 3º Andar",
            capacidade = 30,
            imageUrl = "",
            hasProjector = true,
            hasWhiteboard = true,
            hasAirConditioning = true,
            hasWifi = true,
            hasVideoConference = true
        ),
        Sala(
            id = "5",
            nome = "Sala de Criatividade",
            endereco = "Bloco Inovação - 1º Andar",
            capacidade = 15,
            imageUrl = "",
            hasProjector = false,
            hasWhiteboard = true,
            hasAirConditioning = true,
            hasWifi = true,
            hasVideoConference = false
        ),
        Sala(
            id = "6",
            nome = "Sala de Videoconferência",
            endereco = "Bloco Tecnologia - 2º Andar",
            capacidade = 12,
            imageUrl = "",
            hasProjector = true,
            hasWhiteboard = false,
            hasAirConditioning = true,
            hasWifi = true,
            hasVideoConference = true
        )
    )


    MaterialTheme {
        Surface {
            BookingTab(salas, onViewAllClick = {})
        }
    }
}