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

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = service.login(email, senha)
            _loading.value = false

            if (result.isSuccess) {
                _usuario.value = result.getOrThrow()
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
            }
        }
    }


    fun logout() = service.logout()
}