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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.VideoChat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.Shapes

@Composable
fun ExpandableRoomCard(
    roomName: String,
    address: String,
    capacity: String,
    imageUrl: String,
    hasProjector: Boolean = false,
    hasWhiteboard: Boolean = false,
    hasAirConditioning: Boolean = false,
    hasWifi: Boolean = false,
    hasVideoConference: Boolean = false,
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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = roomName,
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
                    text = address,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (expandedState) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(188.dp),
                    contentScale = ContentScale.Fit,
                )

                Column {
                    Text(
                        text = "Localização",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = address,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column {
                    Text(
                        text = "Capacidade",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = capacity,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column {
                    Text(
                        text = "Recursos",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Column {
                        if (hasProjector) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 4.dp),
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = "Videocam",
                                    tint = Brand400
                                )

                                Text(
                                    text = "Projetor",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }

                        if (hasWhiteboard) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 4.dp),
                                    imageVector = Icons.Outlined.SpaceDashboard,
                                    contentDescription = "Whiteboard",
                                    tint = Brand400
                                )

                                Text(
                                    text = "Quadro branco",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }

                        if (hasAirConditioning) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 4.dp),
                                    imageVector = Icons.Default.AcUnit,
                                    contentDescription = "Air conditioning",
                                    tint = Brand400
                                )

                                Text(
                                    text = "Ar-condicionado",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }

                        if (hasWifi) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 4.dp),
                                    imageVector = Icons.Default.Wifi,
                                    contentDescription = "Wi-fi",
                                    tint = Brand400
                                )

                                Text(
                                    text = "Wi-fi",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }

                        if (hasVideoConference) {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .padding(end = 4.dp),
                                    imageVector = Icons.Default.VideoChat,
                                    contentDescription = "Videochat",
                                    tint = Brand400
                                )

                                Text(
                                    text = "Videoconferência",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }

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
                                text = "Agendar",
                                onClick = primaryButtonAction
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandableRoomCardPreview() {
    ExpandableRoomCard(
        roomName = "Sala de Reuniões Executiva",
        address = "Av. Paulista, 1000 - São Paulo, SP",
        capacity = "10 pessoas",
        imageUrl = "",
        hasProjector = true,
        hasWhiteboard = true,
        hasAirConditioning = true,
        hasWifi = true,
        hasVideoConference = false,
        primaryButtonAction = { }
    )
}