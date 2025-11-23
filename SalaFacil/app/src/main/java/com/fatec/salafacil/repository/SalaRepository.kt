package com.fatec.salafacil.repository

import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.service.SalaService

class SalaRepository(
    private val salaService: SalaService
) {

    suspend fun listarSalas(): Result<List<Sala>> {
        return salaService.listarSalas()
    }

    suspend fun obterSala(id: String): Result<Sala?> {
        return salaService.obterSala(id)
    }

    suspend fun criarSala(sala: Sala): Result<Unit> {
        return salaService.criarSala(sala)
    }

    suspend fun atualizarSala(sala: Sala): Result<Unit> {
        return salaService.atualizarSala(sala)
    }

    suspend fun excluirSala(id: String): Result<Unit> {
        return salaService.excluirSala(id)
    }
}
