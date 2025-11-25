package com.fatec.salafacil.ui.screens.rooms.validators

import com.fatec.salafacil.ui.screens.rooms.formstate.SalaFormState

fun validateNome(nome: String): String? {
    return when {
        nome.isBlank() -> "Nome da sala é obrigatório"
        nome.length < 2 -> "Nome deve ter pelo menos 2 caracteres"
        else -> null
    }
}

fun validateEndereco(endereco: String): String? {
    return when {
        endereco.isBlank() -> "Endereço é obrigatório"
        endereco.length < 5 -> "Endereço deve ter pelo menos 5 caracteres"
        else -> null
    }
}

fun validateCapacidade(capacidade: String): String? {
    return when {
        capacidade.isBlank() -> "Capacidade é obrigatória"
        capacidade.toIntOrNull() == null -> "Capacidade deve ser um número válido"
        capacidade.toInt() <= 0 -> "Capacidade deve ser maior que zero"
        capacidade.toInt() > 1000 -> "Capacidade não pode ser maior que 1000"
        else -> null
    }
}

fun validateImageUrl(imageUrl: String): String? {
    return when {
        imageUrl.isBlank() -> null // URL da imagem é opcional
        !imageUrl.startsWith("http") -> "URL da imagem deve ser válida"
        else -> null
    }
}

fun validateSalaForm(formState: SalaFormState): Pair<SalaFormState, Boolean> {
    val nomeError = validateNome(formState.nome)
    val enderecoError = validateEndereco(formState.endereco)
    val capacidadeError = validateCapacidade(formState.capacidade)
    val imageUrlError = validateImageUrl(formState.imageUrl)

    val updatedFormState = formState.copy(
        nomeError = nomeError,
        enderecoError = enderecoError,
        capacidadeError = capacidadeError,
        imageUrlError = imageUrlError
    )

    val isValid = listOf(
        nomeError,
        enderecoError,
        capacidadeError,
        imageUrlError
    ).all { it == null }

    return updatedFormState to isValid
}