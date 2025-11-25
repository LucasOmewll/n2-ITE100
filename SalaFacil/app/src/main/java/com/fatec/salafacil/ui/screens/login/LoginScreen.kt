package com.fatec.salafacil.ui.screens.login

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.R
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

// Classe para representar o estado do formulário
data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null
)

// Funções de validação
fun validateEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email é obrigatório"
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email inválido"
        else -> null
    }
}

fun validatePassword(password: String): String? {
    return when {
        password.isBlank() -> "Senha é obrigatória"
        password.length < 8 -> "Senha deve ter pelo menos 6 caracteres"
        else -> null
    }
}

@Composable
fun LoginScreen(
    onPasswordRecoveryClick: () -> Unit,
    onLoginButtonClick: (String, String) -> Unit, // Agora recebe email e senha
    onSignUpClick: () -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    // Estado do formulário
    var formState by remember {
        mutableStateOf(LoginFormState())
    }

    // Função para validar todos os campos
    fun validateForm(): Boolean {
        val emailError = validateEmail(formState.email)
        val passwordError = validatePassword(formState.password)

        formState = formState.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        return emailError == null && passwordError == null
    }

    // Função para lidar com o login
    fun handleLogin() {
        if (validateForm()) {
            onLoginButtonClick(formState.email, formState.password)
        }
    }

    Scaffold(
        topBar = {
            Image(
                modifier = Modifier.Companion
                    .height(200.dp)
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.login_img),
                contentDescription = null,
                contentScale = ContentScale.Companion.Crop
            )
        },
        bottomBar = {
            /* Botão */
            Column(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(10.dp, end = 10.dp, top = 10.dp, bottom = 32.dp)
            ) {
                PrimaryButton(
                    text = PT.login_button,
                    modifier = Modifier.Companion.fillMaxWidth(),
                    onClick = { handleLogin() },
                    enabled = formState.email.isNotBlank() && formState.password.isNotBlank()
                )

                // Link para login
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Não possui uma conta? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey500
                    )
                    Text(
                        text = "Cadastrar-se",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Brand400,
                        fontWeight = FontWeight.Companion.Medium,
                        modifier = Modifier.Companion.clickable { onSignUpClick() }
                    )
                }
            }
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                /* Formulário */
                Column(
                    modifier = Modifier.Companion
                        .weight(6f)
                        .padding(10.dp, end = 10.dp, top = 10.dp, bottom = 60.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Companion.CenterHorizontally
                ) {
                    Text(
                        text = PT.login_title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Grey500
                    )

                    Spacer(Modifier.Companion.height(16.dp))

                    // Campo de Email
                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = { email ->
                            formState = formState.copy(
                                email = email,
                                emailError = if (formState.emailError != null) {
                                    validateEmail(email)
                                } else null
                            )
                        },
                        modifier = Modifier.Companion.fillMaxWidth(),
                        label = { Text("Email") },
                        isError = formState.emailError != null,
                        supportingText = {
                            formState.emailError?.let { error ->
                                Text(
                                    text = PT.login_email_error,
                                    color = ErrorColor
                                )
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Brand400,
                            focusedBorderColor = Brand400
                        )
                    )

                    Spacer(Modifier.Companion.height(12.dp))

                    // Campo de Senha
                    OutlinedTextField(
                        value = formState.password,
                        onValueChange = { password ->
                            formState = formState.copy(
                                password = password,
                                passwordError = if (formState.passwordError != null) {
                                    validatePassword(password)
                                } else null
                            )
                        },
                        modifier = Modifier.Companion.fillMaxWidth(),
                        label = { Text("Senha") },
                        isError = formState.passwordError != null,
                        supportingText = {
                            formState.passwordError?.let { error ->
                                Text(
                                    text = PT.login_password_error,
                                    color = ErrorColor
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility) {
                            VisualTransformation.Companion.None
                        } else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(
                                    imageVector = if (passwordVisibility)
                                        Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisibility) "Ocultar senha" else "Mostrar senha"
                                )
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Brand400,
                            focusedBorderColor = Brand400
                        )
                    )

                    Spacer(Modifier.Companion.height(12.dp))

                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = PT.login_reset_password,
                            style = MaterialTheme.typography.bodySmall,
                            color = Grey500,
                            modifier = Modifier.Companion.clickable { onPasswordRecoveryClick() }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        Surface {
            LoginScreen(
                onPasswordRecoveryClick = {},
                onLoginButtonClick = { email, password -> },
                onSignUpClick = {}
            )
        }
    }
}