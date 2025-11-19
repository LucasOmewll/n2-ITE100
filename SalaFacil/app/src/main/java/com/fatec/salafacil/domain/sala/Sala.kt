package com.fatec.salafacil.domain.sala

import com.fatec.salafacil.domain.sala.enum.RecursoSala
import java.time.LocalDateTime
import java.util.UUID

class Sala {
    private val id: UUID = UUID.randomUUID()
    private var nome: String
    private var capacidade: Int
    private var localizacao: String
    private var recursos: List<RecursoSala>
    private var fotoUrl: String
    private var ativa: Boolean
    private var createdAt: LocalDateTime = LocalDateTime.now()

    constructor(
        nome: String,
        capacidade: Int,
        localizacao: String,
        recursos: List<RecursoSala>,
        fotoUrl: String,
        ativa: Boolean,
        createdAt: LocalDateTime
    ) {
        this.nome = nome
        this.capacidade = capacidade
        this.localizacao = localizacao
        this.recursos = recursos
        this.fotoUrl = fotoUrl
        this.ativa = ativa
        this.createdAt = createdAt
    }
}