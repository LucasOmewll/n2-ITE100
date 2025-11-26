package com.fatec.salafacil.ui.screens.home.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatec.salafacil.controller.reuniao.ReuniaoController
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

// Aba de Reuniões
@Composable
fun MeetingsTab(
    reuniaoController: ReuniaoController = viewModel(),
    usuarioId: String
) {

    // Observa as reuniões do usuário
    val reunioes by reuniaoController.reunioes.collectAsState()

    // Carrega as reuniões ao entrar na tela
    LaunchedEffect(usuarioId) {
        reuniaoController.carregarReunioesDoUsuario(usuarioId)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = PT.meetings_tab_label,
                style = MaterialTheme.typography.bodyMedium,
                color = Grey500
            )
        }

        // Lista de Reuniões marcadas pelo usuário
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(reunioes.size) { index ->
                val reuniao = reunioes[index]
                Text(
                    text = "${reuniao.titulo} - ${reuniao.pauta}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MeetingsTabPreview() {
    MaterialTheme {
        Surface {

        }
    }
}