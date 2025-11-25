package com.fatec.salafacil.service.reuniao

import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.model.reuniao.membro.MembroReuniao
import com.fatec.salafacil.repository.ReuniaoRepository
import com.fatec.salafacil.repository.sala.SalaRepository
import com.fatec.salafacil.repository.usuario.UsuarioRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReuniaoService(
    private val reuniaoRepo: ReuniaoRepository = ReuniaoRepository(),
    private val salaRepo: SalaRepository = SalaRepository(),
    private val usuarioRepo: UsuarioRepository = UsuarioRepository()
) {

    /**
     * Valida e cria reunião. Regras:
     * - título obrigatório
     * - dataHoraInicio < dataHoraTermino
     * - checar conflito na sala
     * - preencher membrosIds automaticamente
     */
    suspend fun criarReuniao(reuniao: Reuniao): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (reuniao.titulo.isBlank()) return@withContext Result.failure(Exception("Título obrigatório."))
            if (reuniao.dataHoraInicio.seconds >= reuniao.dataHoraTermino.seconds)
                return@withContext Result.failure(Exception("Horário de término deve ser após início."))

            // checar se sala existe
            val salaRes = salaRepo.obterSala(reuniao.salaId)
            if (salaRes.isFailure) return@withContext Result.failure(Exception("Sala não encontrada."))

            // preencher membrosIds automaticamente se vazio
            if (reuniao.membros.isNotEmpty()) {
                reuniao.membrosIds = reuniao.membros.map { it.userId }
            }

            // checar conflitos via repository (ele já faz)
            val existConflict = reuniaoRepo.existeConflitoPublic(reuniao.salaId, reuniao.dataHoraInicio, reuniao.dataHoraTermino, null)
            if (existConflict) return@withContext Result.failure(Exception("Conflito de horário na sala selecionada."))

            reuniaoRepo.criarReuniao(reuniao)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Atualiza reunião aplicando as mesmas validações.
     */
    suspend fun atualizarReuniao(reuniao: Reuniao): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (reuniao.titulo.isBlank()) return@withContext Result.failure(Exception("Título obrigatório."))
            if (reuniao.dataHoraInicio.seconds >= reuniao.dataHoraTermino.seconds)
                return@withContext Result.failure(Exception("Horário de término deve ser após início."))

            reuniao.membrosIds = reuniao.membros.map { it.userId }

            // checar conflito ignorando própria reunião:
            val conflito = reuniaoRepo.existeConflitoPublic(reuniao.salaId, reuniao.dataHoraInicio, reuniao.dataHoraTermino, reuniao.id)
            if (conflito) return@withContext Result.failure(Exception("Conflito de horário na sala selecionada."))

            reuniaoRepo.atualizarReuniao(reuniao)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun excluirReuniao(salaId: String, reuniaoId: String): Result<Unit> = withContext(Dispatchers.IO) {
        reuniaoRepo.excluirReuniao(salaId, reuniaoId)
    }

    suspend fun listarReunioesDaSala(salaId: String) = withContext(Dispatchers.IO) {
        Result.success(reuniaoRepo.reunioesDaSala(salaId))
    }

    suspend fun listarReunioesDoUsuario(userId: String) = withContext(Dispatchers.IO) {
        Result.success(reuniaoRepo.reunioesDoUsuario(userId))
    }

    // funções auxiliares para adicionar/remover membros
    suspend fun adicionarMembro(reuniao: Reuniao, membro: MembroReuniao): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val membros = reuniao.membros.toMutableList()
            if (membros.any { it.userId == membro.userId }) return@withContext Result.failure(Exception("Membro já adicionado."))
            membros.add(membro)
            reuniao.membros = membros
            reuniao.membrosIds = reuniao.membros.map { it.userId }
            reuniaoRepo.atualizarReuniao(reuniao)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removerMembro(reuniao: Reuniao, userId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val membros = reuniao.membros.filter { it.userId != userId }
            reuniao.membros = membros
            reuniao.membrosIds = membros.map { it.userId }
            reuniaoRepo.atualizarReuniao(reuniao)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
