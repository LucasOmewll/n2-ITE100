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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatec.salafacil.controller.auth.AuthController
import com.fatec.salafacil.controller.reuniao.ReuniaoController
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.reuniao.membros.MembroReuniao
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
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun BookRoomForMeetingScreen(
    sala: Sala?,
    onConfirmClick: () -> Unit,
    onBackClicked: () -> Unit,
    authController: AuthController = viewModel(),
    reuniaoController: ReuniaoController = viewModel()
) {
    val erro by reuniaoController.erro.collectAsState()

    var formState by remember {
        mutableStateOf(MeetingFormState())
    }

    LaunchedEffect(Unit) {
        authController.carregarUsuarioAtual()
    }

    val usuarioAtual by authController.usuario.collectAsState()


    var questionIndex by remember { mutableIntStateOf(0) }
    var previousIndex by remember { mutableIntStateOf(0) }
    val goingForward = questionIndex > previousIndex

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val bookRoomTabs = listOf(
        ProcessTab(
            title = PT.book_room_check_location,
            body = PT.book_room_check_location_body,
            value = sala?.endereco,
            icon = Icons.Default.LocationOn
        ),
        ProcessTab(
            title = PT.book_room_check_capacity,
            body = PT.book_room_check_capacity_body,
            value = sala?.capacidade.toString(),
            icon = Icons.Default.Groups
        ),
        ProcessTab(
            title = PT.book_room_check_resources,
            body = PT.book_room_check_resources_body,
            icon = Icons.Default.AutoAwesome,
            hasProjector = sala?.hasProjector,
            hasWhiteboard = sala?.hasWhiteboard,
            hasAirConditioning = sala?.hasAirConditioning,
            hasWifi = sala?.hasWifi,
            hasVideoConference = sala?.hasVideoConference
        ),
    )

    LaunchedEffect(questionIndex) {
        previousIndex = questionIndex
    }

    // Efeito para mostrar mensagens de erro
    LaunchedEffect(erro) {
        reuniaoController.erro.value?.let { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
            reuniaoController.limparErro()
        }
    }

    fun criarReuniao() {

        if (usuarioAtual == null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Usuário não autenticado")
            }
            return
        }

        val novaReuniao = Reuniao(
            id = UUID.randomUUID().toString(),
            titulo = formState.titulo,
            pauta = formState.pauta,
            data = formState.data,
            dataHoraInicio = formState.horarioInicio,
            dataHoraTermino = formState.horarioTermino,
            salaId = sala?.id ?: "",
            createdBy = usuarioAtual!!.id,
            membros = mutableListOf(
                MembroReuniao(
                    userId = usuarioAtual!!.id,
                    email = usuarioAtual!!.email,
                    role = "ORGANIZADOR"
                )
            )
        )

        reuniaoController.criarReuniao(novaReuniao)

        coroutineScope.launch {
            onConfirmClick()
        }
    }

    fun validarFormulario(): MeetingFormState {
        var novoEstado = formState.copy(
            tituloError = null,
            pautaError = null,
            dataError = null,
            horarioInicioError = null,
            horarioTerminoError = null,
            intervaloError = null
        )

        // Validar título
        if (formState.titulo.isBlank()) {
            novoEstado = novoEstado.copy(tituloError = "Título é obrigatório")
        } else if (formState.titulo.length < 3) {
            novoEstado = novoEstado.copy(tituloError = "Título deve ter pelo menos 3 caracteres")
        }

        // Validar pauta
        if (formState.pauta.isBlank()) {
            novoEstado = novoEstado.copy(pautaError = "Pauta é obrigatória")
        } else if (formState.pauta.length < 10) {
            novoEstado = novoEstado.copy(pautaError = "Pauta deve ter pelo menos 10 caracteres")
        }

        // Validar data (não pode ser no passado)
        val agora = Timestamp.now()
        if (formState.data.seconds < agora.seconds) {
            novoEstado = novoEstado.copy(dataError = "Data não pode ser no passado")
        }

        // Validar horário de início
        if (formState.horarioInicio.seconds <= 0) {
            novoEstado = novoEstado.copy(horarioInicioError = "Horário de início é obrigatório")
        }

        // Validar horário de término
        if (formState.horarioTermino.seconds <= 0) {
            novoEstado = novoEstado.copy(horarioTerminoError = "Horário de término é obrigatório")
        }

        // Validar intervalo de tempo (término deve ser depois do início)
        if (formState.horarioInicio.seconds > 0 && formState.horarioTermino.seconds > 0) {
            if (formState.horarioTermino.seconds <= formState.horarioInicio.seconds) {
                novoEstado = novoEstado.copy(
                    intervaloError = "Horário de término deve ser após o horário de início"
                )
            }

            // Validar se a duração é razoável (máximo 8 horas)
            val duracaoSegundos = formState.horarioTermino.seconds - formState.horarioInicio.seconds
            val duracaoHoras = duracaoSegundos / 3600.0
            if (duracaoHoras > 8) {
                novoEstado = novoEstado.copy(
                    intervaloError = "A reunião não pode durar mais de 8 horas"
                )
            }

            // Validar duração mínima (pelo menos 15 minutos)
            if (duracaoSegundos < 900) {
                novoEstado = novoEstado.copy(
                    intervaloError = "A reunião deve durar pelo menos 15 minutos"
                )
            }
        }

        return novoEstado
    }

    fun handleNextClick() {
        when (questionIndex) {
            in 0..2 -> questionIndex++
            3 -> {
                val estadoValidado = validarFormulario()

                // Atualiza o formState com os erros de validação
                formState = estadoValidado

                // Verifica se não há erros
                val isValid = estadoValidado.tituloError == null &&
                        estadoValidado.pautaError == null &&
                        estadoValidado.dataError == null &&
                        estadoValidado.horarioInicioError == null &&
                        estadoValidado.horarioTerminoError == null &&
                        estadoValidado.intervaloError == null

                if (isValid) {
                    criarReuniao()
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Corrija os erros antes de continuar")
                    }
                }
            }
        }
    }

    fun handleBackClick() {
        when {
            questionIndex > 0 -> questionIndex--
            questionIndex == 0 -> onBackClicked()
        }
    }

    fun getButtonText(): String {
        return when (questionIndex) {
            3 -> PT.book_room_finalize_button
            else -> PT.book_room_confirm_button
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = PT.book_room_title,
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
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Retornar"
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, end = 10.dp, top = 10.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedSecondaryButton(
                    modifier = Modifier.weight(1f),
                    text = PT.book_room_return_button,
                    onClick = { handleBackClick() }
                )

                Spacer(Modifier.width(40.dp))

                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    text = getButtonText(),
                    onClick = { handleNextClick() }
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FormProgressBar(questionIndex, 4)

            Spacer(Modifier.height(40.dp))

            Text(
                text = when (questionIndex) {
                    3 -> PT.book_room_fill_details_label
                    else -> PT.book_room_check_label
                },
                style = MaterialTheme.typography.titleMedium,
                color = Grey400
            )

            Spacer(Modifier.height(40.dp))

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
        id = "1",
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