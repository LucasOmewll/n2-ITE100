package com.fatec.salafacil.ui.screens.meetings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.fatec.salafacil.ui.components.NotImplementedDialog
import com.fatec.salafacil.ui.components.OutlinedDangerButton
import com.fatec.salafacil.ui.components.OutlinedSecondaryButton
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

@Composable
fun MeetingDetailsScreen(
    onBackClicked: () -> Unit,
    imagemSalaUrl: String,
    localizacaoSala: String,
    nomeSala: String,
    titulo: String,
    data: String,
    horarioInicio: String,
    horarioTermino: String,
    assunto: String,
    onEditButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onShareButtonClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.Companion
            .fillMaxSize()
            .padding(10.dp),
        topBar = {
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    modifier = Modifier.Companion.weight(6f),
                    text = PT.meeting_details_title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )

                IconButton(
                    modifier = Modifier.Companion.weight(1f),
                    onClick = {
                        onBackClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Retornar"
                    )
                }
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier.Companion.padding(contentPadding),
        ) {

            Spacer(modifier = Modifier.Companion.height(16.dp))

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Grey500
                )

                Column(
                    horizontalAlignment = Alignment.Companion.End
                ) {
                    Text(
                        text = data,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Grey500
                    )
                    Text(
                        text = "$horarioInicio - $horarioTermino",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Grey400
                    )
                }
            }

            Image(
                painter = rememberAsyncImagePainter(imagemSalaUrl),
                contentDescription = null,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .height(188.dp),
                contentScale = ContentScale.Companion.Crop,
            )

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Assunto Section
            Text(
                text = PT.meeting_details_assunto,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

            Text(
                text = assunto,
                style = MaterialTheme.typography.bodyMedium,
                color = Grey500
            )

            Spacer(modifier = Modifier.Companion.height(12.dp))

            Text(
                text = PT.meeting_details_sala,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

            Text(
                text = PT.meeting_details_sala_name,
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
                    imageVector = Icons.Outlined.Business,
                    contentDescription = "Localização",
                    tint = Grey400,
                )

                Text(
                    text = nomeSala,
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey400
                )
            }

            Spacer(modifier = Modifier.Companion.height(6.dp))

            Text(
                text = PT.meeting_details_sala_location,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

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
                    text = localizacaoSala,
                    style = MaterialTheme.typography.bodySmall,
                    color = Grey400
                )
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Opções
            Text(
                text = PT.meeting_details_sala_options,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                OutlinedSecondaryButton(
                    text = PT.meeeting_detials_edit_button,
                    onClick = { onEditButtonClicked() }
                )
                Spacer(modifier = Modifier.Companion.width(10.dp))
                OutlinedDangerButton(
                    text = PT.meeeting_detials_cancel_button,
                    onClick = { onCancelButtonClicked() }
                )
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Retornar
            Spacer(modifier = Modifier.Companion.height(24.dp))
            OutlinedSecondaryButton(
                modifier = Modifier.Companion.fillMaxWidth(),
                text = "Voltar",
                onClick = { onBackClicked() }
            )
        }
    }
}


@Preview
@Composable
fun MeetingDetailsScreenPreview() {
    MaterialTheme {
        Surface {
            MeetingDetailsScreen(
                onBackClicked = {},
                imagemSalaUrl = "https://picsum.photos/300",
                nomeSala = "Palace CoWorking",
                localizacaoSala = "R. Bell Aliance 69 - São Caetano do Sul",
                titulo = "Reunião Estratégica",
                data = "15/12/2025",
                horarioInicio = "14:00",
                horarioTermino = "16:00",
                assunto = "Planejamento do próximo trimestre",
                onEditButtonClicked = {},
                onCancelButtonClicked = {},
                onShareButtonClicked = {}
            )
        }
    }
}