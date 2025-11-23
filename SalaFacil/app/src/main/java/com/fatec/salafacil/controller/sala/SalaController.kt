package com.fatec.salafacil.controller.sala

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.sala.Sala
import com.fatec.salafacil.repository.SalaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalaController(
    private val salaRepository: SalaRepository
) : ViewModel() {

    private val _salas = MutableStateFlow<List<Sala>>(emptyList())
    val salas: StateFlow<List<Sala>> get() = _salas

    private val _salaSelecionada = MutableStateFlow<Sala?>(null)
    val salaSelecionada: StateFlow<Sala?> get() = _salaSelecionada

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> get() = _erro

    fun carregarSalas() {
        viewModelScope.launch {
            salaRepository.listarSalas()
                .onSuccess { _salas.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun carregarSala(id: String) {
        viewModelScope.launch {
            salaRepository.obterSala(id)
                .onSuccess { _salaSelecionada.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun criarSala(sala: Sala) {
        viewModelScope.launch {
            salaRepository.criarSala(sala)
                .onFailure { _erro.value = it.message }
        }
    }

    fun atualizarSala(sala: Sala) {
        viewModelScope.launch {
            salaRepository.atualizarSala(sala)
                .onFailure { _erro.value = it.message }
        }
    }

    fun excluirSala(id: String) {
        viewModelScope.launch {
            salaRepository.excluirSala(id)
                .onFailure { _erro.value = it.message }
        }
    }
}
