package com.fatec.salafacil.repository.sala

import com.fatec.salafacil.model.sala.Sala
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SalaRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val salasRef = db.collection("salas")

    suspend fun listarSalas(): Result<List<Sala>> {
        return try {
            val snapshot = salasRef.get().await()
            val salas = snapshot.toObjects(Sala::class.java)
            Result.success(salas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun obterSala(id: String): Result<Sala> {
        return try {
            val doc = salasRef.document(id).get().await()
            val sala = doc.toObject(Sala::class.java)
                ?: return Result.failure(Exception("Sala não encontrada."))

            Result.success(sala)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun criarSala(sala: Sala): Result<Unit> {
        return try {
            salasRef.document(sala.id).set(sala).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun atualizarSala(sala: Sala): Result<Unit> {
        return try {
            salasRef.document(sala.id).set(sala).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removerSala(id: String): Result<Unit> {
        return try {
            salasRef.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
