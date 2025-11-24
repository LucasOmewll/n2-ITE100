package com.fatec.salafacil.ui.screens.meetings.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.VideoChat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.translations.PT

@Composable
fun BookRoomProcessTab(processTab: ProcessTab) {
    val resourceIcons: List<Pair<ImageVector, String>> = buildList {
        if (processTab.hasProjector == true)
            add(Icons.Default.Videocam to "Projetor")

        if (processTab.hasWhiteboard == true)
            add(Icons.Outlined.SpaceDashboard to "Quadro branco")

        if (processTab.hasAirConditioning == true)
            add(Icons.Default.AcUnit to "Ar-condicionado")

        if (processTab.hasWifi == true)
            add(Icons.Default.Wifi to "Wi-Fi")

        if (processTab.hasVideoConference == true)
            add(Icons.Default.VideoChat to "Videoconferência")
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = processTab.title,
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(Modifier.width(40.dp))

        Icon(
            modifier = Modifier.size(120.dp),
            imageVector = processTab.icon,
            contentDescription = processTab.title,
            tint = Brand500
        )

        Spacer(Modifier.width(64.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = processTab.body,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = Grey400
        )

        Spacer(Modifier.width(10.dp))

        if (processTab.value != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = processTab.value.toString(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Grey400
            )
        }

        if (resourceIcons.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                resourceIcons.forEach { (icon, label) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = icon, contentDescription = label, tint = Brand400)
                        Text(text = label, style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(Modifier.width(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun BookRoomProcessTabPreview() {
    val sala = Sala(
        nome = "Sala 203 - Bloco B",
        endereco = "Av. Paulista, 1500",
        capacidade = 40,
        hasProjector = true,
        hasWhiteboard = true,
        hasWifi = true,
        hasAirConditioning = true,
        hasVideoConference = true
    )

    val bookRoomTabs = listOf(
        ProcessTab(
            title = PT.book_room_check_location,
            body = PT.book_room_check_location_body,
            value = sala.endereco,
            icon = Icons.Default.LocationOn
        ),
        ProcessTab(
            title = PT.book_room_check_capacity,
            body = PT.book_room_check_capacity_body,
            value = sala.capacidade.toString(),
            icon = Icons.Default.Groups
        ),
        ProcessTab(
            title = PT.book_room_check_resources,
            body = PT.book_room_check_resources_body,
            icon = Icons.Default.AutoAwesome,
            hasProjector = sala.hasProjector,
            hasWhiteboard = sala.hasWhiteboard,
            hasAirConditioning = sala.hasAirConditioning,
            hasWifi = sala.hasWifi,
            hasVideoConference = sala.hasVideoConference
        )
    )

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BookRoomProcessTab(processTab = bookRoomTabs[1])
        }
    }
}