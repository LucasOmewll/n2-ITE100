package com.fatec.salafacil.ui.screens.meetings

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.ui.components.FormProgressBar
import com.fatec.salafacil.ui.components.MeetingForm
import com.fatec.salafacil.ui.components.OutlinedSecondaryButton
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.screens.meetings.formstate.MeetingFormState
import com.fatec.salafacil.ui.screens.meetings.tabs.BookRoomProcessTab
import com.fatec.salafacil.ui.screens.meetings.tabs.ProcessTab
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.translations.PT

@Composable
fun BookRoomForMeetingScreen(
    sala: Sala,
    onConfirmClick: (Reuniao) -> Unit,
    onBackClicked: () -> Unit
) {
    var formState by remember {
        mutableStateOf(MeetingFormState())
    }

    var questionIndex by remember { mutableIntStateOf(0) }

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
        ),
    )

    var previousIndex by remember { mutableIntStateOf(0) }

    val goingForward = questionIndex > previousIndex


    LaunchedEffect(questionIndex) {
        previousIndex = questionIndex
    }

    Scaffold(
        modifier = Modifier.Companion
            .fillMaxSize()
            .padding(10.dp),
        topBar = {
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    modifier = Modifier.Companion.weight(6f),
                    text = PT.book_room_title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Companion.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )

                IconButton(
                    modifier = Modifier.Companion.weight(1f), onClick = {
                        onBackClicked()
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Retornar"
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(10.dp, end = 10.dp, top = 10.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedSecondaryButton(
                    modifier = Modifier.Companion.weight(1f),
                    text = PT.book_room_return_button,
                    onClick = {
                        if (questionIndex > 0) {
                            questionIndex--
                        }

                        if (questionIndex == 1) {
                            onBackClicked()
                        }
                    }
                )

                Spacer(Modifier.Companion.width(40.dp))

                PrimaryButton(
                    modifier = Modifier.Companion
                        .weight(1f),
                    text = PT.book_room_confirm_button,
                    onClick = {
                        if (questionIndex < 3) {
                            questionIndex++
                        }
                    }
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.Companion.padding(contentPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            FormProgressBar(questionIndex, 4)

            Spacer(Modifier.Companion.height(40.dp))

            Text(
                text = PT.book_room_check_label,
                style = MaterialTheme.typography.titleMedium,
                color = Grey400
            )

            Spacer(Modifier.Companion.height(40.dp))

            AnimatedContent(
                targetState = questionIndex,
                transitionSpec = {
                    if (goingForward) {
                        slideInHorizontally { it } togetherWith
                                slideOutHorizontally { -it }
                    } else {
                        slideInHorizontally { -it } togetherWith
                                slideOutHorizontally { it }
                    }.using(SizeTransform(clip = false))
                }
            ) { targetIndex ->
                when (targetIndex) {
                    0, 1, 2 -> Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BookRoomProcessTab(processTab = bookRoomTabs[targetIndex])
                    }
                    3 -> MeetingForm(
                        formState = formState,
                        onFormChange = { newState ->
                            formState = newState
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BookRoomForMeetingPreview() {
    val fakeSala = Sala(
        nome = "Sala 203 - Bloco B",
        endereco = "Av. Paulista, 1500",
        capacidade = 40,
        hasProjector = true,
        hasWhiteboard = true
    )

    MaterialTheme {
        Surface {
            BookRoomForMeetingScreen(
                sala = fakeSala,
                onBackClicked = {},
                onConfirmClick = {}
            )
        }
    }
}