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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatec.salafacil.controller.sala.SalaController
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.components.ExpandableRoomCard
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.Grey300
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

// Tela de Reservar Sala
@Composable
fun BookingTab(
    isAdmin: Boolean,
    onCreateClick: () -> Unit,
    onBookClicked: () -> Unit,
    salaController: SalaController = viewModel(),
) {
    val salas by salaController.salas.collectAsState()
    val loading by salaController.loading.collectAsState()

    LaunchedEffect(Unit) {
        salaController.carregarSalas()
    }

    Column(
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

            if (isAdmin) {
                Text(
                    text = PT.booking_tab_clickable,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey300,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onCreateClick() }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                loading -> {
                    // Exibe loading
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Brand500
                    )
                }

                salas.isEmpty() -> {
                    // Nenhuma sala disponível
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
                else -> {
                    // Lista de salas
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(
                            items = salas,
                            key = { sala -> sala.id ?: sala.hashCode() }
                        ) { sala ->
                            ExpandableRoomCard(
                                sala = sala,
                                primaryButtonAction = { onBookClicked() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BookingTabPreview() {
    MaterialTheme {
        Surface {
            BookingTab(
                isAdmin = true,
                onBookClicked = {},
                onCreateClick = {}
            )
        }
    }
}