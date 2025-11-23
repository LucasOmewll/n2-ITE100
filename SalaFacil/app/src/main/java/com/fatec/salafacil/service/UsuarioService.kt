package com.fatec.salafacil.service

import com.fatec.salafacil.model.usuario.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UsuarioService(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val usuariosRef = db.collection("usuarios")

    suspend fun criarUsuario(usuario: Usuario): Result<Unit> {
        return try {
            usuariosRef.document(usuario.id).set(usuario).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun buscarUsuario(id: String): Result<Usuario?> {
        return try {
            val doc = usuariosRef.document(id).get().await()
            Result.success(doc.toObject(Usuario::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun listarUsuarios(): Result<List<Usuario>> {
        return try {
            val snapshot = usuariosRef.get().await()
            Result.success(snapshot.toObjects(Usuario::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun atualizarUsuario(usuario: Usuario): Result<Unit> {
        return try {
            usuariosRef.document(usuario.id).set(usuario).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
