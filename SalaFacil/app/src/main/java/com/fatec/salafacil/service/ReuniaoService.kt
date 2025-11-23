package com.fatec.salafacil.service

import com.fatec.salafacil.model.reuniao.Reuniao
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReuniaoService(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val reunioesRef = db.collection("reunioes")

    private suspend fun existeConflito(salaId: String, inicio: Timestamp, fim: Timestamp): Boolean {
        val snapshot = reunioesRef
            .whereEqualTo("salaId", salaId)
            .get()
            .await()

        val reunioes = snapshot.toObjects(Reuniao::class.java)

        return reunioes.any { r ->
            (inicio.seconds < r.fim.seconds) && (fim.seconds > r.inicio.seconds)
        }
    }

    suspend fun criarReuniao(reuniao: Reuniao): Result<Unit> {
        return try {
            val temConflito = existeConflito(reuniao.salaId, reuniao.inicio, reuniao.fim)
            if (temConflito) {
                return Result.failure(Exception("Conflito de horário na sala selecionada."))
            }

            reunioesRef.document(reuniao.id).set(reuniao).await()
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listarReunioes(): Result<List<Reuniao>> {
        return try {
            val snapshot = reunioesRef.get().await()
            Result.success(snapshot.toObjects(Reuniao::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listarReunioesPorUsuario(usuarioId: String): Result<List<Reuniao>> {
        return try {
            val snapshot = reunioesRef
                .whereArrayContains("participantes", usuarioId)
                .get()
                .await()

            Result.success(snapshot.toObjects(Reuniao::class.java))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletarReuniao(id: String): Result<Unit> {
        return try {
            reunioesRef.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarReuniaoPorId(id: String): Result<Reuniao?> {
        return try {
            val snapshot = reunioesRef.document(id).get().await()
            Result.success(snapshot.toObject(Reuniao::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
