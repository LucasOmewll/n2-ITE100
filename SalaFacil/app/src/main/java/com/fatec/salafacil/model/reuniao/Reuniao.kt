package com.fatec.salafacil.model.reuniao

import com.fatec.salafacil.model.reuniao.membros.MembroReuniao
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import java.util.UUID

data class Reuniao(
    val id: String = UUID.randomUUID().toString(),
    val salaId: String = "",

    var active: Boolean = true,
    var titulo: String = "",
    var pauta: String = "",
    var diaInteiro: Boolean = false,


    var data: Timestamp = Timestamp.now(),
    var dataHoraInicio: Timestamp = Timestamp.now(),
    var dataHoraTermino: Timestamp = Timestamp.now(),


    var createdBy: DocumentReference? = null,
    var membros: List<MembroReuniao> = emptyList(),


    // Campo auxiliar necessário para pesquisas
    var membrosIds: List<String> = emptyList(),

    val createdAt: Timestamp = Timestamp.now()
)
