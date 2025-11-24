package com.fatec.salafacil.repository

import com.fatec.salafacil.model.reuniao.Reuniao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReuniaoRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val salasCollection = db.collection("salas")

    // -------------------------------------------------------------
    // 1. Criar reunião (em uma sala específica)
    // -------------------------------------------------------------
    suspend fun criarReuniao(reuniao: Reuniao): Boolean {
        return try {
            // Atualiza o campo indexável
            val reuniaoAtualizada = reuniao.copy(
                membrosIds = reuniao.membros.map { it.userId }
            )

            salasCollection
                .document(reuniao.salaId)
                .collection("reunioes")
                .document(reuniao.id)
                .set(reuniaoAtualizada)
                .await()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    // -------------------------------------------------------------
    // 2. Buscar reuniões de uma sala
    // -------------------------------------------------------------
    suspend fun reunioesDaSala(salaId: String): List<Reuniao> {
        return try {
            val snapshot = salasCollection
                .document(salaId)
                .collection("reunioes")
                .get()
                .await()

            snapshot.toObjects(Reuniao::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    // -------------------------------------------------------------
    // 3. Buscar todas as reuniões em que um usuário participa
    // -------------------------------------------------------------
    suspend fun reunioesDoUsuario(userId: String): List<Reuniao> {
        return try {
            val snapshot = db.collectionGroup("reunioes")
                .whereArrayContains("membrosIds", userId)
                .get()
                .await()

            snapshot.toObjects(Reuniao::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    // -------------------------------------------------------------
    // 4. Atualizar reunião
    // -------------------------------------------------------------
    suspend fun atualizarReuniao(reuniao: Reuniao): Boolean {
        return try {
            val reuniaoAtualizada = reuniao.copy(
                membrosIds = reuniao.membros.map { it.userId }
            )

            salasCollection
                .document(reuniao.salaId)
                .collection("reunioes")
                .document(reuniao.id)
                .set(reuniaoAtualizada)
                .await()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    // -------------------------------------------------------------
    // 5. Deletar reunião
    // -------------------------------------------------------------
    suspend fun deletarReuniao(salaId: String, reuniaoId: String): Boolean {
        return try {
            salasCollection
                .document(salaId)
                .collection("reunioes")
                .document(reuniaoId)
                .delete()
                .await()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // dentro de ReuniaoRepository class
    // método público que reusa a lógica privada
    suspend fun existeConflitoPublic(
        salaId: String,
        inicio: com.google.firebase.Timestamp,
        fim: com.google.firebase.Timestamp,
        excluirReuniaoId: String? = null
    ): Boolean {
        return existeConflito(salaId, inicio, fim, excluirReuniaoId)
    }

}
