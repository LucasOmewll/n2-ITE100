package com.fatec.salafacil.service

import com.fatec.salafacil.model.sala.Sala
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SalaService(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val salasRef = db.collection("salas")

    suspend fun criarSala(sala: Sala): Result<Unit> {
        return try {
            salasRef.document(sala.id).set(sala).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listarSalas(): Result<List<Sala>> {
        return try {
            val snapshot = salasRef.get().await()
            val lista = snapshot.toObjects(Sala::class.java)
            Result.success(lista)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarSalaPorId(id: String): Result<Sala?> {
        return try {
            val doc = salasRef.document(id).get().await()
            Result.success(doc.toObject(Sala::class.java))
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

    suspend fun deletarSala(id: String): Result<Unit> {
        return try {
            salasRef.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
