package com.fatec.salafacil.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.reuniao.membros.MembroReuniao
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.screens.meetings.utils.toDateString
import com.fatec.salafacil.ui.screens.meetings.utils.toHourString
import com.fatec.salafacil.ui.theme.Grey200
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Shapes
import com.google.firebase.Timestamp

@Composable
fun ExpandableMeetingCard(
    sala: Sala,
    reuniao: Reuniao,
    primaryButtonAction: () -> Unit
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = Shapes.medium,
        onClick = {
            expandedState = !expandedState
        },
        colors = CardDefaults.cardColors(
            containerColor = Grey200
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = reuniao.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-down arrow"
                    )
                }
            }
            Row {
                Text(
                    text = reuniao.data.toDateString(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (expandedState) {
                Image(
                    painter = rememberAsyncImagePainter(sala.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(188.dp),
                    contentScale = ContentScale.Fit
                )
                Row {
                    Text(
                        text = reuniao.titulo,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Grey400,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${reuniao.data.toDateString()} : ${reuniao.dataHoraInicio.toHourString()} - ${reuniao.dataHoraTermino.toHourString()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey400,
                        maxLines = 1,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = reuniao.pauta,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey400,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedSecondaryButton(
                            text = "Fechar",
                            onClick = {
                                expandedState = !expandedState
                            }
                        )

                        PrimaryButton(
                            text = "Detalhes",
                            onClick = primaryButtonAction
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableMeetingCardPreview() {

    val fakeSala = Sala(
        nome = "Sala 204",
        endereco = "Bloco B",
        capacidade = 30,
        imageUrl = ""
    )

}

