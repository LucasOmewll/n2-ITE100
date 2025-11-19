package com.fatec.salafacil.models.usuario.model

import com.fatec.salafacil.models.usuario.enums.TipoUsuario
import java.time.LocalDateTime
import java.util.UUID

class Usuario  {
    private val id: UUID = UUID.randomUUID()
    private var nome: String
    private var email: String
    private var senha: String
    private var tipo: TipoUsuario
    private var createdAt: LocalDateTime = LocalDateTime.now()

    constructor(
        nome: String,
        email: String,
        senha: String,
        tipo: TipoUsuario,
        createdAt: LocalDateTime
    ) {
        this.nome = nome
        this.email = email
        this.senha = senha
        this.tipo = tipo
        this.createdAt = createdAt
    }
}