package com.fatec.salafacil.controller.sala

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.service.SalaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalaController(
    private val salaService: SalaService
) : ViewModel() {

    private val _salas = MutableStateFlow<List<Sala>>(emptyList())
    val salas: StateFlow<List<Sala>> get() = _salas

    private val _salaSelecionada = MutableStateFlow<Sala?>(null)
    val salaSelecionada: StateFlow<Sala?> get() = _salaSelecionada

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> get() = _erro

    fun carregarSalas() {
        viewModelScope.launch {
            salaService.listarSalas()
                .onSuccess { _salas.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun carregarSala(id: String) {
        viewModelScope.launch {
            salaService.obterSala(id)
                .onSuccess { _salaSelecionada.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun criarSala(sala: Sala) {
        viewModelScope.launch {
            salaService.criarSala(sala)
                .onFailure { _erro.value = it.message }
        }
    }

    fun atualizarSala(sala: Sala) {
        viewModelScope.launch {
            salaService.atualizarSala(sala)
                .onFailure { _erro.value = it.message }
        }
    }

    fun excluirSala(id: String) {
        viewModelScope.launch {
            salaService.excluirSala(id)
                .onFailure { _erro.value = it.message }
        }
    }
}
