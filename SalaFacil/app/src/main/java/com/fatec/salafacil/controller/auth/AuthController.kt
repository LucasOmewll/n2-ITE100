package com.fatec.salafacil.controller.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.usuario.Usuario
import com.fatec.salafacil.service.auth.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthController(private val service: AuthService = AuthService()) : ViewModel() {

    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    private val _registrado = MutableStateFlow(false)
    val registrado: StateFlow<Boolean> = _registrado

    private val _logado = MutableStateFlow<Boolean?>(null)
    val logado: StateFlow<Boolean?> = _logado

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = service.login(email, senha)
            _loading.value = false

            if (result.isSuccess) {
                _usuario.value = result.getOrThrow()
                _logado.value = true
            } else {
                _erro.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun registrar(usuario: Usuario, senha: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = service.registrar(usuario, senha)
            _loading.value = false

            if (result.isSuccess) {
                _usuario.value = result.getOrThrow()
                _registrado.value = true
            } else {
                _erro.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun carregarUsuarioAtual() {
        viewModelScope.launch {
            val result = service.carregarUsuarioAtual()
            if (result.isSuccess) {
                _usuario.value = result.getOrThrow()
            } else {
                _usuario.value = null
            }
        }
    }

    suspend fun enviarEmailRecuperacaoSenha(email: String) {
        service.enviarEmailRecuperacaoSenha(email)
    }

    fun recuperarSenha(email: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = service.enviarEmailRecuperacaoSenha(email)
            _loading.value = false

            if (result.isSuccess) {
                _erro.value = "Email de recuperação enviado com sucesso!"
            } else {
                _erro.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun limparErro() {
        _erro.value = null
    }

    fun resetarRegistro() {
        _registrado.value = false
    }

    fun logout() {
        service.logout()
        _logado.value = false
        _usuario.value = null
    }
}