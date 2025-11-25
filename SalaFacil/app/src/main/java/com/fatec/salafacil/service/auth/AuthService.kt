package com.fatec.salafacil.service.auth

import com.fatec.salafacil.model.usuario.Usuario
import com.fatec.salafacil.repository.auth.AuthRepository

class AuthService(private val repository: AuthRepository = AuthRepository()) {

    suspend fun login(email: String, senha: String): Result<Usuario> {
        val uidResult  = repository.login(email, senha)

        if (uidResult.isFailure) return Result.failure(uidResult.exceptionOrNull()!!)

        val uid = uidResult.getOrThrow()
        return repository.carregarUsuario(uid)
    }

    suspend fun registrar(usuario: Usuario, senha: String): Result<Usuario> {
        val uidResult = repository.registrarUsuario(usuario, senha)
        if (uidResult.isFailure) return Result.failure(uidResult.exceptionOrNull()!!)

        val uid = uidResult.getOrThrow()
        return repository.carregarUsuario(uid)
    }

    suspend fun carregarUsuarioAtual(): Result<Usuario> {
        val uid = repository.usuarioAtualId() ?: return Result.failure(Exception("Nenhum usuário logado."))
        return repository.carregarUsuario(uid)
    }

    fun logout() = repository.logout()
}
