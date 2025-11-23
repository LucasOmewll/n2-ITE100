package com.fatec.salafacil.repository

import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.service.ReuniaoService

class ReuniaoRepository(
    private val reuniaoService: ReuniaoService
) {

    suspend fun listarReunioes(salaId: String): Result<List<Reuniao>> {
        return reuniaoService.listarReunioes(salaId)
    }

    suspend fun obterReuniao(salaId: String, idReuniao: String): Result<Reuniao?> {
        return reuniaoService.obterReuniao(salaId, idReuniao)
    }

    suspend fun criarReuniao(reuniao: Reuniao): Result<Unit> {
        return reuniaoService.criarReuniao(reuniao)
    }

    suspend fun atualizarReuniao(reuniao: Reuniao): Result<Unit> {
        return reuniaoService.atualizarReuniao(reuniao)
    }

    suspend fun excluirReuniao(salaId: String, idReuniao: String): Result<Unit> {
        return reuniaoService.excluirReuniao(salaId, idReuniao)
    }
}
