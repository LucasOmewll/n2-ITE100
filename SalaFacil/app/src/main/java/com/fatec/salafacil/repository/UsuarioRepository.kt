package com.fatec.salafacil.repository

import com.fatec.salafacil.model.usuario.Usuario
import com.fatec.salafacil.service.UsuarioService

class UsuarioRepository(
    private val usuarioService: UsuarioService
) {

    suspend fun obterUsuario(id: String): Result<Usuario?> {
        return usuarioService.obterUsuario(id)
    }

    suspend fun listarUsuarios(): Result<List<Usuario>> {
        return usuarioService.listarUsuarios()
    }

    suspend fun criarUsuario(usuario: Usuario): Result<Unit> {
        return usuarioService.criarUsuario(usuario)
    }

    suspend fun atualizarUsuario(usuario: Usuario): Result<Unit> {
        return usuarioService.atualizarUsuario(usuario)
    }

    suspend fun excluirUsuario(id: String): Result<Unit> {
        return usuarioService.excluirUsuario(id)
    }
}
