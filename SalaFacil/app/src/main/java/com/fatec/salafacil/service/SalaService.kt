package com.fatec.salafacil.service.sala

import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.repository.sala.SalaRepository

class SalaService(
    private val repository: SalaRepository = SalaRepository()
) {

    suspend fun listarSalas(): Result<List<Sala>> =
        repository.listarSalas()

    suspend fun obterSala(id: String): Result<Sala> =
        repository.obterSala(id)

    suspend fun criarSala(sala: Sala): Result<Unit> {
        if (sala.nome.isBlank()) return Result.failure(Exception("Nome obrigatório."))
        if (sala.endereco.isBlank()) return Result.failure(Exception("Endereço obrigatório."))
        if (sala.capacidade <= 0) return Result.failure(Exception("Capacidade inválida."))

        return repository.criarSala(sala)
    }

    suspend fun atualizarSala(sala: Sala): Result<Unit> =
        repository.atualizarSala(sala)

    suspend fun removerSala(id: String): Result<Unit> =
        repository.removerSala(id)
}
