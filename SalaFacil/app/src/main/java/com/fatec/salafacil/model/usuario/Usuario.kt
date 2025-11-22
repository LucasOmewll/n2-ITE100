package com.fatec.salafacil.model.usuario

import com.fatec.salafacil.model.usuario.enums.Role
import com.google.firebase.Timestamp
import java.util.UUID

data class Usuario (
    val id: String = UUID.randomUUID().toString(),
    var nome: String = "",
    var email: String = "",
    var telefone: String = "",
    var role: Role = Role.GUEST,
    val criadoEm: Timestamp = Timestamp.now()
)