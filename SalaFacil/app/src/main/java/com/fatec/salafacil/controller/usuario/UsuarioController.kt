package com.fatec.salafacil.controller.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.usuario.Usuario
import com.fatec.salafacil.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioController(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> get() = _usuario

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> get() = _usuarios

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> get() = _erro

    fun carregarUsuario(id: String) {
        viewModelScope.launch {
            usuarioRepository.obterUsuario(id)
                .onSuccess { _usuario.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun carregarUsuarios() {
        viewModelScope.launch {
            usuarioRepository.listarUsuarios()
                .onSuccess { _usuarios.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun criarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioRepository.criarUsuario(usuario)
                .onFailure { _erro.value = it.message }
        }
    }

    fun atualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioRepository.atualizarUsuario(usuario)
                .onFailure { _erro.value = it.message }
        }
    }

    fun excluirUsuario(id: String) {
        viewModelScope.launch {
            usuarioRepository.excluirUsuario(id)
                .onFailure { _erro.value = it.message }
        }
    }
}
