package com.fatec.salafacil.repository

import com.fatec.salafacil.model.reuniao.Reuniao
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReuniaoRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val salasCollection = db.collection("salas")

    suspend fun criarReuniao(reuniao: Reuniao): Boolean {
        return try {
            val atualizada = reuniao.copy(
                membrosIds = reuniao.membros.map { it.userId }
            )

            salasCollection
                .document(reuniao.salaId)
                .collection("reunioes")
                .document(reuniao.id)
                .set(atualizada)
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun reunioesDaSala(salaId: String): List<Reuniao> {
        return try {
            val snap = salasCollection
                .document(salaId)
                .collection("reunioes")
                .get()
                .await()

            snap.toObjects(Reuniao::class.java)
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun reunioesDoUsuario(userId: String): List<Reuniao> {
        return try {
            val snap = db.collectionGroup("reunioes")
                .whereArrayContains("membrosIds", userId)
                .get()
                .await()

            snap.toObjects(Reuniao::class.java)
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun atualizarReuniao(reuniao: Reuniao): Boolean {
        return try {
            val atualizada = reuniao.copy(
                membrosIds = reuniao.membros.map { it.userId }
            )

            salasCollection
                .document(reuniao.salaId)
                .collection("reunioes")
                .document(reuniao.id)
                .set(atualizada)
                .await()

            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun deletarReuniao(salaId: String, reuniaoId: String): Boolean {
        return try {
            salasCollection
                .document(salaId)
                .collection("reunioes")
                .document(reuniaoId)
                .delete()
                .await()

            true
        } catch (_: Exception) {
            false
        }
    }

    private suspend fun existeConflito(
        salaId: String,
        inicio: Timestamp,
        fim: Timestamp,
        excluir: String? = null
    ): Boolean {
        return try {
            val snap = salasCollection
                .document(salaId)
                .collection("reunioes")
                .whereLessThan("dataHoraTermino", fim)
                .whereGreaterThan("dataHoraInicio", inicio)
                .get()
                .await()

            val lista = snap.toObjects(Reuniao::class.java)

            val filtrada = if (excluir != null)
                lista.filter { it.id != excluir }
            else lista

            filtrada.isNotEmpty()
        } catch (_: Exception) {
            false
        }
    }

    suspend fun existeConflitoPublic(
        salaId: String,
        inicio: Timestamp,
        fim: Timestamp,
        excluir: String? = null
    ) = existeConflito(salaId, inicio, fim, excluir)
}
