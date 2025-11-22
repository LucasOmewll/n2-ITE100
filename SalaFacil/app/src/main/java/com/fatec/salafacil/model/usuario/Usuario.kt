package com.fatec.salafacil.model.usuario

import com.fatec.salafacil.model.usuario.enums.Role
import com.google.firebase.Timestamp
import java.util.UUID

class Usuario (
    val id: UUID = UUID.randomUUID(),
    var nome: String,
    var email: String,
    var telefone: String,
    var role: Role,
    val criadoEm: Timestamp = Timestamp.now()
)