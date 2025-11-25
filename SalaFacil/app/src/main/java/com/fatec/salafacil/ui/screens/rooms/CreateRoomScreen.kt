// CreateRoomScreen.kt
package com.fatec.salafacil.ui.screens.rooms

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.components.SalaForm
import com.fatec.salafacil.ui.screens.rooms.formstate.SalaFormState
import com.fatec.salafacil.ui.screens.rooms.validators.validateSalaForm
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomScreen(
    onBackClicked: () -> Unit,
    onSaveButtonClicked: (Sala) -> Unit
) {
    var formState by remember {
        mutableStateOf(SalaFormState())
    }

    fun validateForm(): Boolean {
        val (updatedState, isValid) = validateSalaForm(formState)
        formState = updatedState
        return isValid
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = PT.create_room_title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onBackClicked() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            SalaForm(
                formState = formState,
                onFormChange = { updated ->
                    formState = updated
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão de Salvar
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = PT.create_save_button,
                onClick = {
                    if (validateForm()) {
                        val novaSala = Sala(
                            nome = formState.nome,
                            endereco = formState.endereco,
                            capacidade = formState.capacidade.toInt(),
                            imageUrl = formState.imageUrl,
                            hasProjector = formState.hasProjector,
                            hasWhiteboard = formState.hasWhiteboard,
                            hasAirConditioning = formState.hasAirConditioning,
                            hasWifi = formState.hasWifi,
                            hasVideoConference = formState.hasVideoConference
                        )
                        onSaveButtonClicked(novaSala)
                    }
                },
                enabled = formState.nome.isNotBlank() &&
                        formState.endereco.isNotBlank() &&
                        formState.capacidade.isNotBlank() &&
                        formState.capacidade.toIntOrNull() != null
            )
        }
    }
}

@Preview
@Composable
fun CreateRoomScreenPreview() {
    MaterialTheme {
        Surface {
            CreateRoomScreen(
                onBackClicked = {},
                onSaveButtonClicked = {}
            )
        }
    }
}