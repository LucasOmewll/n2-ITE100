package com.fatec.salafacil.service.reuniao

import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.reuniao.membros.MembroReuniao
import com.fatec.salafacil.repository.reuniao.ReuniaoRepository
import com.fatec.salafacil.repository.sala.SalaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReuniaoService(
    private val reuniaoRepo: ReuniaoRepository = ReuniaoRepository(),
    private val salaRepo: SalaRepository = SalaRepository()
) {

    suspend fun criarReuniao(reuniao: Reuniao): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (reuniao.titulo.isBlank()) return@withContext Result.failure(Exception("Título obrigatório"))
            if (reuniao.dataHoraInicio.seconds >= reuniao.dataHoraTermino.seconds)
                return@withContext Result.failure(Exception("Horário inválido"))

            val sala = salaRepo.obterSala(reuniao.salaId)
            if (sala.isFailure) return@withContext Result.failure(Exception("Sala não encontrada"))

            reuniao.membrosIds = reuniao.membros.map { it.userId }

            val conflitante = reuniaoRepo.existeConflitoPublic(
                reuniao.salaId,
                reuniao.dataHoraInicio,
                reuniao.dataHoraTermino
            )

            if (conflitante)
                return@withContext Result.failure(Exception("Conflito de horário na sala"))

            reuniaoRepo.criarReuniao(reuniao)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun atualizarReuniao(reuniao: Reuniao): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (reuniao.titulo.isBlank()) return@withContext Result.failure(Exception("Título obrigatório"))

            val conflito = reuniaoRepo.existeConflitoPublic(
                reuniao.salaId,
                reuniao.dataHoraInicio,
                reuniao.dataHoraTermino,
                excluir = reuniao.id
            )

            if (conflito) return@withContext Result.failure(Exception("Conflito de horário"))

            reuniao.membrosIds = reuniao.membros.map { it.userId }
            reuniaoRepo.atualizarReuniao(reuniao)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun excluirReuniao(salaId: String, reuniaoId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            val ok = reuniaoRepo.deletarReuniao(salaId, reuniaoId)
            if (ok) Result.success(Unit)
            else Result.failure(Exception("Erro ao excluir reunião"))
        }

    suspend fun listarReunioesDaSala(salaId: String) =
        withContext(Dispatchers.IO) { Result.success(reuniaoRepo.reunioesDaSala(salaId)) }

    suspend fun listarReunioesDoUsuario(userId: String) =
        withContext(Dispatchers.IO) { Result.success(reuniaoRepo.reunioesDoUsuario(userId)) }

    suspend fun adicionarMembro(reuniao: Reuniao, membro: MembroReuniao): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val membros = reuniao.membros.toMutableList()
                if (membros.any { it.userId == membro.userId })
                    return@withContext Result.failure(Exception("Membro já existe"))

                membros.add(membro)
                reuniao.membros = membros
                reuniao.membrosIds = membros.map { it.userId }

                reuniaoRepo.atualizarReuniao(reuniao)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun removerMembro(reuniao: Reuniao, userId: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val novos = reuniao.membros.filter { it.userId != userId }
                reuniao.membros = novos
                reuniao.membrosIds = novos.map { it.userId }

                reuniaoRepo.atualizarReuniao(reuniao)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
