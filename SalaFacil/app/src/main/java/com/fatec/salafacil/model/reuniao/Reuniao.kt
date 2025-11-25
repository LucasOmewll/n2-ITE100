package com.fatec.salafacil.model.reuniao

import com.google.firebase.Timestamp
import java.util.UUID

data class Reuniao(
    val id: String = UUID.randomUUID().toString(),
    val salaId: String = "",

    var active: Boolean = true,
    var titulo: String = "",
    var pauta: String = "",
    var diaInteiro: Boolean = false,

    var dataHoraInicio: Timestamp = Timestamp.now(),
    var dataHoraTermino: Timestamp = Timestamp.now(),

    var inicio: Timestamp = Timestamp.now(),
    var fim: Timestamp = Timestamp.now(),

    // Campo auxiliar necessário para pesquisas
    var membrosIds: List<String> = emptyList(),

    val createdAt: Timestamp = Timestamp.now()
)
