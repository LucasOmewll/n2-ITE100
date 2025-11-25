package com.fatec.salafacil.repository

import com.fatec.salafacil.model.usuario.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UsuarioRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val usuariosRef = db.collection("usuarios")

    suspend fun criarUsuario(usuario: Usuario): Result<Unit> = try {
        usuariosRef.document(usuario.id).set(usuario).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun obterUsuario(id: String): Result<Usuario?> = try {
        val doc = usuariosRef.document(id).get().await()
        Result.success(doc.toObject(Usuario::class.java))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun listarUsuarios(): Result<List<Usuario>> = try {
        val snap = usuariosRef.get().await()
        Result.success(snap.toObjects(Usuario::class.java))
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun atualizarUsuario(usuario: Usuario): Result<Unit> = try {
        usuariosRef.document(usuario.id).set(usuario).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun excluirUsuario(id: String): Result<Unit> = try {
        usuariosRef.document(id).delete().await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
