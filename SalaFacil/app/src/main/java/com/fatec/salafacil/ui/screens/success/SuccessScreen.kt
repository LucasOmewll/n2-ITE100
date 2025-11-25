package com.fatec.salafacil.ui.screens.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

@Composable
fun SuccessScreen(
    title: String,
    description: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = PT.success_back_button,
                onClick = onBackClick
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Grey500,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Ícone grande de sucesso
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Sucesso",
                tint = Brand400,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Descrição
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Grey500,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Versão alternativa com mais opções de personalização
@Composable
fun CustomSuccessScreen(
    title: String,
    description: String,
    onBackClick: () -> Unit,
    icon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Sucesso",
            tint = Brand400,
            modifier = Modifier.size(120.dp)
        )
    },
    buttonText: String = PT.success_back_button,
    iconColor: Color = Brand400,
    iconSize: Int = 120
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = buttonText,
                onClick = onBackClick
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Grey500,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Ícone customizável
            icon()

            Spacer(modifier = Modifier.height(32.dp))

            // Descrição
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Grey500,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun SuccessScreenPreview() {
    MaterialTheme {
        Surface {
            SuccessScreen(
                title = "Sala Cadastrada!",
                description = "A sala foi cadastrada com sucesso e já está disponível para agendamento de reuniões.",
                onBackClick = {}
            )
        }
    }
}

@Preview
@Composable
fun SuccessScreenPreview2() {
    MaterialTheme {
        Surface {
            SuccessScreen(
                title = "Reunião Agendada!",
                description = "Sua reunião foi agendada com sucesso. Um e-mail de confirmação foi enviado para todos os participantes.",
                onBackClick = {}
            )
        }
    }
}

@Preview
@Composable
fun CustomSuccessScreenPreview() {
    MaterialTheme {
        Surface {
            CustomSuccessScreen(
                title = "Edição Concluída!",
                description = "As informações da sala foram atualizadas com sucesso.",
                onBackClick = {},
                buttonText = "Voltar para a Lista"
            )
        }
    }
}