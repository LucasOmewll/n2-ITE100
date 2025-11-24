package com.fatec.salafacil.ui.screens.rooms.formstate

data class SalaFormState(
    val nome: String = "",
    val nomeError: String? = null,

    val endereco: String = "",
    val enderecoError: String? = null,

    val capacidade: String = "",
    val capacidadeError: String? = null,

    val imageUrl: String = "",
    val imageUrlError: String? = null,

    val hasProjector: Boolean = false,
    val hasWhiteboard: Boolean = false,
    val hasAirConditioning: Boolean = false,
    val hasWifi: Boolean = false,
    val hasVideoConference: Boolean = false
)
