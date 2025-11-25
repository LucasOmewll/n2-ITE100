package com.fatec.salafacil.model.sala

import com.google.firebase.Timestamp
import java.util.UUID

data class Sala(
    val id: String = UUID.randomUUID().toString(),
    var nome: String = "",
    var endereco: String = "",
    var capacidade: Int = 0,
    var imageUrl: String = "",

    var hasProjector: Boolean = false,
    var hasWhiteboard: Boolean = false,
    var hasAirConditioning: Boolean = false,
    var hasWifi: Boolean = false,
    var hasVideoConference: Boolean = false,

    val createdAt: Timestamp = Timestamp.now(),
    var createdBy: String = ""
)
