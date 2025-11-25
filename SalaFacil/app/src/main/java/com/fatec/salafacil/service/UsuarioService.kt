package com.fatec.salafacil.service.usuario

import com.fatec.salafacil.model.usuario.Usuario
import com.fatec.salafacil.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioService(
    private val repo: UsuarioRepository = UsuarioRepository()
) {

    suspend fun criarUsuario(usuario: Usuario): Result<Unit> = withContext(Dispatchers.IO) {
        if (usuario.email.isBlank()) return@withContext Result.failure(Exception("Email obrigatório"))
        if (usuario.nome.isBlank()) return@withContext Result.failure(Exception("Nome obrigatório"))

        repo.criarUsuario(usuario)
    }

    suspend fun obterUsuario(id: String) =
        withContext(Dispatchers.IO) { repo.obterUsuario(id) }

    suspend fun listarUsuarios() =
        withContext(Dispatchers.IO) { repo.listarUsuarios() }

    suspend fun atualizarUsuario(usuario: Usuario): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (usuario.nome.isBlank()) return@withContext Result.failure(Exception("Nome obrigatório"))
            repo.atualizarUsuario(usuario)
        }

    suspend fun excluirUsuario(id: String) =
        withContext(Dispatchers.IO) { repo.excluirUsuario(id) }
}
