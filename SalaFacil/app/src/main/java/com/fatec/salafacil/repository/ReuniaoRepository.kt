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

    fun criarReuniao(
        salaId: String,
        reuniao: Reuniao,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        reuniao.membrosIds = reuniao.membros.map { it.userId }

        firestore.collection("salas")
            .document(salaId)
            .collection("reunioes")
            .document(reuniao.id)
            .set(reuniao)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener(onFailure)
    }

    fun getReunioesDoUsuario(
        userId: String,
        onSuccess: (List<Reuniao>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collectionGroup("reunioes")
            .whereArrayContains("membrosIds", userId)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { doc ->
                    doc.toObject(Reuniao::class.java)?.copy(
                        id = doc.id,
                        salaId = doc.reference.parent.parent?.id ?: ""
                    )
                }
                onSuccess(lista)
            }
            .addOnFailureListener(onFailure)
    }


}
