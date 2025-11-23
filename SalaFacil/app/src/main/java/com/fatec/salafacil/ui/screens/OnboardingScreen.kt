package com.fatec.salafacil.ui.screens

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.R
import com.fatec.salafacil.ui.theme.Grey500
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.fatec.salafacil.ui.components.PrimaryButton
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.translations.PT

// Classe para representar o estado do formulário de cadastro
data class OnboardingFormState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

// Funções de validação para cadastro
fun validateName(name: String): String? {
    return when {
        name.isBlank() -> "Nome é obrigatório"
        name.length < 2 -> "Nome deve ter pelo menos 2 caracteres"
        name.any { it.isDigit() } -> "Nome não deve conter números"
        else -> null
    }
}

fun validateOnboardingEmail(email: String): String? {
    return when {
        email.isBlank() -> "Email é obrigatório"
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email inválido"
        else -> null
    }
}

fun validateOnboardingPassword(password: String): String? {
    return when {
        password.isBlank() -> "Senha é obrigatória"
        password.length < 8 -> "Senha deve ter pelo menos 8 caracteres"
        !password.any { it.isUpperCase() } -> "Senha deve conter pelo menos uma letra maiúscula"
        !password.any { it.isDigit() } -> "Senha deve conter pelo menos um número"
        !password.any { it in "!@#$%^&*()_+-=[]{}|;:,.<>?" } -> "Senha deve conter pelo menos um caractere especial"
        else -> null
    }
}

@Composable
fun OnboardingScreen(
    onLoginClick: () -> Unit, // Para navegar para a tela de login
    onSignUpButtonClick: (String, String, String) -> Unit // Recebe nome, email e senha
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    // Estado do formulário
    var formState by remember {
        mutableStateOf(OnboardingFormState())
    }

    // Cores personalizadas para os campos
    val focusedColor = Color(0xFF1E88E5) // Azul
    val unfocusedColor = Grey500

    // Função para validar todos os campos
    fun validateForm(): Boolean {
        val nameError = validateName(formState.name)
        val emailError = validateOnboardingEmail(formState.email)
        val passwordError = validateOnboardingPassword(formState.password)

        formState = formState.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError
        )

        return nameError == null && emailError == null && passwordError == null
    }

    // Função para lidar com o cadastro
    fun handleSignUp() {
        if (validateForm()) {
            onSignUpButtonClick(formState.name, formState.email, formState.password)
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.create_profile_img),
                contentDescription = "Imagem de cadastro de usuários",
                contentScale = ContentScale.Crop
            )

            /* Formulário */
            Column(
                modifier = Modifier
                    .weight(6f)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = PT.onboarding_title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Grey500
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = PT.onboarding_subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey500
                )

                Spacer(Modifier.height(24.dp))

                // Campo de Nome
                OutlinedTextField(
                    value = formState.name,
                    onValueChange = { name ->
                        formState = formState.copy(
                            name = name,
                            nameError = if (formState.nameError != null) {
                                validateName(name)
                            } else null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nome completo") },
                    isError = formState.nameError != null,
                    supportingText = {
                        formState.nameError?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red
                            )
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brand400,
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Campo de Email
                OutlinedTextField(
                    value = formState.email,
                    onValueChange = { email ->
                        formState = formState.copy(
                            email = email,
                            emailError = if (formState.emailError != null) {
                                validateOnboardingEmail(email)
                            } else null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    isError = formState.emailError != null,
                    supportingText = {
                        formState.emailError?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red
                            )
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Brand400,
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Campo de Senha
                OutlinedTextField(
                    value = formState.password,
                    onValueChange = { password ->
                        formState = formState.copy(
                            password = password,
                            passwordError = if (formState.passwordError != null) {
                                validateOnboardingPassword(password)
                            } else null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Senha") },
                    isError = formState.passwordError != null,
                    supportingText = {
                        formState.passwordError?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red
                            )
                        } ?: run {
                            Text(
                                text = PT.onboarding_password_supporting_text,
                                color = Grey500
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) {
                        VisualTransformation.None
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
                        focusedBorderColor = Brand400,
                    )
                )

                Spacer(Modifier.height(24.dp))

                // Botão de Cadastro
                PrimaryButton(
                    text = "Cadastrar",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { handleSignUp() },
                    enabled = formState.name.isNotBlank() &&
                            formState.email.isNotBlank() &&
                            formState.password.isNotBlank()
                )

                Spacer(Modifier.height(16.dp))

                // Link para login
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Já tem uma conta? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Grey500
                    )
                    Text(
                        text = "Fazer login",
                        style = MaterialTheme.typography.bodyMedium,
                        color = focusedColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { onLoginClick() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    MaterialTheme {
        Surface {
            OnboardingScreen(
                onLoginClick = {},
                onSignUpButtonClick = { name, email, password -> }
            )
        }
    }
}