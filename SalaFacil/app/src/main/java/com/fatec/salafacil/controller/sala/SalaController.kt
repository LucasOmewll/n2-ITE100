package com.fatec.salafacil.controller.sala

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.service.sala.SalaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalaController(
    private val service: SalaService = SalaService()
) : ViewModel() {

    private val _salas = MutableStateFlow<List<Sala>>(emptyList())
    val salas: StateFlow<List<Sala>> = _salas

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun carregarSalas() {
        viewModelScope.launch {
            _loading.value = true
            val result = service.listarSalas()
            _loading.value = false

            if (result.isSuccess) _salas.value = result.getOrThrow()
            else _erro.value = result.exceptionOrNull()?.message
        }
    }

    fun criarSala(sala: Sala) {
        viewModelScope.launch {
            val result = service.criarSala(sala)
            if (result.isFailure) {
                _erro.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun atualizarSala(sala: Sala) {
        viewModelScope.launch {
            val result = service.atualizarSala(sala)
            if (result.isFailure) {
                _erro.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun removerSala(id: String) {
        viewModelScope.launch {
            val result = service.removerSala(id)
            if (result.isFailure) {
                _erro.value = result.exceptionOrNull()?.message
            }
        }
    }
}
