package com.fatec.salafacil.model.reuniao

import com.google.firebase.Timestamp
import java.util.UUID

data class Reuniao(
    val id: String = UUID.randomUUID().toString(),
    val salaId: String = "",

    var titulo: String = "",
    var descricao: String = "",

    var responsavelId: String = "",
    var participantes: List<String> = emptyList(),

    var data: Timestamp = Timestamp.now(),
    var inicio: Timestamp = Timestamp.now(),
    var fim: Timestamp = Timestamp.now(),

    val criadoEm: Timestamp = Timestamp.now()
)
