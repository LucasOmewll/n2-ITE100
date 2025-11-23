package com.fatec.salafacil.model.sala

import com.google.firebase.Timestamp
import java.util.UUID

data class Sala(
    val id: String = UUID.randomUUID().toString(),

    var nome: String = "",
    var endereco: String = "",
    var assentos: Int = 0,

    var audioVideo: Boolean = false,
    var internet: Boolean = false,
    var imageUrl: String = "",

    var createdBy: String = "",
    val createdAt: Timestamp = Timestamp.now()
)
