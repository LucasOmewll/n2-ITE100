package com.fatec.salafacil.controller.sala

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.service.sala.SalaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalaController(
    private val salaService: SalaService = SalaService()
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
            salaService.listarSalas()
                .onSuccess { _salas.value = it }
                .onFailure { _erro.value = it.message }
            _loading.value = false
        }
    }

    fun criarSala(sala: Sala, usuarioIdCriador: String? = null) {
        viewModelScope.launch {
            salaService.criarSala(sala, usuarioIdCriador)
                .onFailure { _erro.value = it.message }
        }
    }

    fun atualizarSala(sala: Sala) {
        viewModelScope.launch {
            salaService.atualizarSala(sala)
                .onFailure { _erro.value = it.message }
        }
    }

    fun removerSala(id: String) {
        viewModelScope.launch {
            salaService.removerSala(id)
                .onFailure { _erro.value = it.message }
        }
    }
}
