package com.fatec.salafacil.service.sala

import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.repository.sala.SalaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SalaService(
    private val repo: SalaRepository = SalaRepository()
) {

    suspend fun listarSalas() = withContext(Dispatchers.IO) { repo.listarSalas() }

    suspend fun obterSala(id: String) = withContext(Dispatchers.IO) { repo.obterSala(id) }

    suspend fun criarSala(sala: Sala, usuarioIdCriador: String?): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (sala.nome.isBlank()) return@withContext Result.failure(Exception("Nome obrigatório"))
            if (sala.endereco.isBlank()) return@withContext Result.failure(Exception("Endereço obrigatório"))
            if (sala.capacidade <= 0) return@withContext Result.failure(Exception("Capacidade inválida"))

            repo.criarSala(sala)
        }

    suspend fun atualizarSala(sala: Sala) =
        withContext(Dispatchers.IO) { repo.atualizarSala(sala) }

    suspend fun removerSala(id: String) =
        withContext(Dispatchers.IO) { repo.removerSala(id) }
}
