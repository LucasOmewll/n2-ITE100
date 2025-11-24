package com.fatec.salafacil.ui.screens.passwordrecovery

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.components.OutlinedSecondaryButton
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

// Classe para representar o estado do formulário de recuperação de senha
data class PasswordRecoveryFormState(
    val email: String = "",
    val emailError: String? = null,
    val isEmailSent: Boolean = false
)

// Função de validação para email de recuperação
fun validateRecoveryEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email é obrigatório"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email inválido"
        else -> null
    }
}

@Composable
fun PasswordRecoveryScreen(
    onBackClick: () -> Unit,
    onSendRecoveryEmail: (String) -> Unit
) {
    // Estado do formulário
    var formState by remember {
        mutableStateOf(PasswordRecoveryFormState())
    }

    // Cores personalizadas para os campos
    val focusedColor = Color(0xFF1E88E5)
    val unfocusedColor = Grey500

    // Função para validar o email
    fun validateForm(): Boolean {
        val emailError = validateRecoveryEmail(formState.email)

        formState = formState.copy(
            emailError = emailError
        )

        return emailError == null
    }

    // Função para lidar com o envio do email de recuperação
    fun handleSendRecoveryEmail() {
        if (validateForm()) {
            onSendRecoveryEmail(formState.email)
            // Simula o envio bem-sucedido (em uma implementação real, isso viria da API)
            formState = formState.copy(isEmailSent = true)
        }
    }

    Surface(modifier = Modifier.Companion.fillMaxSize()) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = PT.password_recovery_title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Companion.Bold,
                color = Grey500
            )

            Spacer(Modifier.Companion.height(8.dp))

            // Subtítulo
            Text(
                text = if (formState.isEmailSent) {
                    PT.password_recovery_subtitle_success
                } else {
                    PT.password_recovery_subtitle_regular
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Grey500,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.Companion.height(32.dp))

            if (!formState.isEmailSent) {
                // Campo de Email
                OutlinedTextField(
                    value = formState.email,
                    onValueChange = { email ->
                        formState = formState.copy(
                            email = email,
                            emailError = if (formState.emailError != null) {
                                validateRecoveryEmail(email)
                            } else null
                        )
                    },
                    modifier = Modifier.Companion.fillMaxWidth(),
                    label = { Text("Email") },
                    isError = formState.emailError != null,
                    supportingText = {
                        if (formState.emailError != null) {
                            Text(
                                text = formState.emailError!!,
                                color = Color.Companion.Red
                            )
                        } else if (formState.email.isNotEmpty() && validateRecoveryEmail(formState.email) == null) {
                            Text(
                                text = "Email válido ✓",
                                color = Color(0xFF4CAF50)
                            )
                        } else if (formState.email.isEmpty()) {
                            Text(
                                text = PT.password_recovery_supporting_text,
                                color = Grey500
                            )
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = focusedColor,
                        unfocusedBorderColor = unfocusedColor,
                        focusedLabelColor = focusedColor,
                        cursorColor = focusedColor
                    )
                )

                Spacer(Modifier.Companion.height(24.dp))

                // Botão de Enviar Email
                PrimaryButton(
                    text = "Enviar Email de Recuperação",
                    modifier = Modifier.Companion.fillMaxWidth(),
                    onClick = { handleSendRecoveryEmail() },
                    enabled = formState.email.isNotBlank()
                )
            } else {
                // Mensagem de sucesso
                Text(
                    text = "Enviamos um email para ${formState.email} com as instruções para redefinir sua senha.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey500,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.Companion.height(24.dp))

                // Botão de Reenviar (caso o usuário queira enviar para outro email)
                PrimaryButton(
                    text = "Enviar para outro email",
                    modifier = Modifier.Companion.fillMaxWidth(),
                    onClick = {
                        formState = formState.copy(
                            isEmailSent = false,
                            email = "",
                            emailError = null
                        )
                    }
                )
            }

            Spacer(Modifier.Companion.height(16.dp))

            // Botão Secundário de Retornar
            OutlinedSecondaryButton(
                onClick = onBackClick,
                modifier = Modifier.Companion.fillMaxWidth(),
                text = if (formState.isEmailSent) "Voltar para o login" else "Cancelar",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordRecoveryScreenPreview() {
    MaterialTheme {
        Surface {
            PasswordRecoveryScreen(
                onBackClick = {},
                onSendRecoveryEmail = { email -> }
            )
        }
    }
}
