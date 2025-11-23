package com.fatec.salafacil.controller.reuniao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.service.ReuniaoService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReuniaoController(
    private val reuniaoService: ReuniaoService
) : ViewModel() {

    private val _reunioes = MutableStateFlow<List<Reuniao>>(emptyList())
    val reunioes: StateFlow<List<Reuniao>> get() = _reunioes

    private val _reuniaoSelecionada = MutableStateFlow<Reuniao?>(null)
    val reuniaoSelecionada: StateFlow<Reuniao?> get() = _reuniaoSelecionada

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> get() = _erro

    fun carregarReunioes(salaId: String) {
        viewModelScope.launch {
            reuniaoService.listarReunioes(salaId)
                .onSuccess { _reunioes.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun carregarReuniao(salaId: String, reuniaoId: String) {
        viewModelScope.launch {
            reuniaoService.obterReuniao(salaId, reuniaoId)
                .onSuccess { _reuniaoSelecionada.value = it }
                .onFailure { _erro.value = it.message }
        }
    }

    fun criarReuniao(reuniao: Reuniao) {
        viewModelScope.launch {
            reuniaoService.criarReuniao(reuniao)
                .onFailure { _erro.value = it.message }
        }
    }

    fun atualizarReuniao(reuniao: Reuniao) {
        viewModelScope.launch {
            reuniaoService.atualizarReuniao(reuniao)
                .onFailure { _erro.value = it.message }
        }
    }

    fun removerReuniao(salaId: String, reuniaoId: String) {
        viewModelScope.launch {
            reuniaoService.excluirReuniao(salaId, reuniaoId)
                .onFailure { _erro.value = it.message }
        }
    }
}
