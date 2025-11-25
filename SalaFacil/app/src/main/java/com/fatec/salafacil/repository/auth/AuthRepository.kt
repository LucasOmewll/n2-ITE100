package com.fatec.salafacil.repository.auth

import com.fatec.salafacil.model.usuario.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun login(email: String, senha: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, senha).await()
            Result.success(result.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registrarUsuario(usuario: Usuario, senha: String): Result<String> {
        return try {
            val cred = auth.createUserWithEmailAndPassword(usuario.email, senha).await()
            val uid = cred.user?.uid ?: return Result.failure(Exception("UID inexistente."))

            val userDoc = usuario.copy(id = uid)

            db.collection("usuarios").document(uid)
                .set(userDoc)
                .await()

            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun carregarUsuario(uid: String): Result<Usuario> {
        return try {
            val snapshot = db.collection("usuarios").document(uid).get().await()
            val usuario = snapshot.toObject(Usuario::class.java)
                ?: return Result.failure(Exception("Usuário não encontrado."))

            Result.success(usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun usuarioAtualId(): String? = auth.currentUser?.uid
}