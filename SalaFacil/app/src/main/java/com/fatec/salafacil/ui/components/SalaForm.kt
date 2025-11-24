// SalaForm.kt
package com.fatec.salafacil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.screens.rooms.formstate.SalaFormState
import com.fatec.salafacil.ui.screens.rooms.validators.validateCapacidade
import com.fatec.salafacil.ui.screens.rooms.validators.validateEndereco
import com.fatec.salafacil.ui.screens.rooms.validators.validateImageUrl
import com.fatec.salafacil.ui.screens.rooms.validators.validateNome
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalaForm(
    formState: SalaFormState,
    onFormChange: (SalaFormState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = PT.room_info_label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Grey500
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Nome
        OutlinedTextField(
            value = formState.nome,
            onValueChange = { new ->
                onFormChange(
                    formState.copy(
                        nome = new,
                        nomeError = validateNome(new)
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nome da Sala") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.MeetingRoom,
                    contentDescription = "Nome da sala"
                )
            },
            isError = formState.nomeError != null,
            supportingText = {
                formState.nomeError?.let { error ->
                    Text(text = error, color = ErrorColor)
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Brand400
            )
        )

        Spacer(Modifier.height(16.dp))

        // Campo de Endereço
        OutlinedTextField(
            value = formState.endereco,
            onValueChange = { new ->
                onFormChange(
                    formState.copy(
                        endereco = new,
                        enderecoError = validateEndereco(new)
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Endereço") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Place,
                    contentDescription = "Endereço"
                )
            },
            isError = formState.enderecoError != null,
            supportingText = {
                formState.enderecoError?.let { error ->
                    Text(text = error, color = ErrorColor)
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Brand400
            )
        )

        Spacer(Modifier.height(16.dp))

        // Campo de Capacidade
        OutlinedTextField(
            value = formState.capacidade,
            onValueChange = { new ->
                onFormChange(
                    formState.copy(
                        capacidade = new,
                        capacidadeError = validateCapacidade(new)
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Capacidade") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.People,
                    contentDescription = "Capacidade"
                )
            },
            isError = formState.capacidadeError != null,
            supportingText = {
                formState.capacidadeError?.let { error ->
                    Text(text = error, color = ErrorColor)
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Brand400
            )
        )

        Spacer(Modifier.height(16.dp))

        // Campo de URL da Imagem (Opcional)
        OutlinedTextField(
            value = formState.imageUrl,
            onValueChange = { new ->
                onFormChange(
                    formState.copy(
                        imageUrl = new,
                        imageUrlError = validateImageUrl(new)
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("URL da Imagem (Opcional)") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = "URL da imagem"
                )
            },
            isError = formState.imageUrlError != null,
            supportingText = {
                formState.imageUrlError?.let { error ->
                    Text(text = error, color = ErrorColor)
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Brand400
            )
        )

        Spacer(Modifier.height(24.dp))

        // Recursos da Sala
        Text(
            text = PT.room_resources_label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Grey500
        )

        Spacer(Modifier.height(12.dp))

        // Checkboxes para recursos
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Projetor
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasProjector,
                    onCheckedChange = { checked ->
                        onFormChange(formState.copy(hasProjector = checked))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Brand400
                    )
                )
                Text(
                    text = PT.room_projector,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Quadro Branco
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasWhiteboard,
                    onCheckedChange = { checked ->
                        onFormChange(formState.copy(hasWhiteboard = checked))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Brand400
                    )
                )
                Text(
                    text = PT.room_whiteboard,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Ar Condicionado
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasAirConditioning,
                    onCheckedChange = { checked ->
                        onFormChange(formState.copy(hasAirConditioning = checked))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Brand400
                    )
                )
                Text(
                    text = PT.room_air_conditioning,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Wi-Fi
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasWifi,
                    onCheckedChange = { checked ->
                        onFormChange(formState.copy(hasWifi = checked))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Brand400
                    )
                )
                Text(
                    text = PT.room_wifi,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Videoconferência
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formState.hasVideoConference,
                    onCheckedChange = { checked ->
                        onFormChange(formState.copy(hasVideoConference = checked))
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Brand400
                    )
                )
                Text(
                    text = PT.room_video_conference,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SalaFormPreview() {
    MaterialTheme {
        Surface {
            SalaForm(
                formState = SalaFormState(),
                onFormChange = {}
            )
        }
    }
}