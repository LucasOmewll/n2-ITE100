package com.fatec.salafacil.controller.reuniao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatec.salafacil.model.reuniao.Reuniao
import com.fatec.salafacil.service.reuniao.ReuniaoService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReuniaoController(
    private val service: ReuniaoService = ReuniaoService()
) : ViewModel() {

    private val _reunioes = MutableStateFlow<List<Reuniao>>(emptyList())
    val reunioes: StateFlow<List<Reuniao>> get() = _reunioes


    private val _reuniaoSelecionada = MutableStateFlow<Reuniao?>(null)
    val reuniaoSelecionada: StateFlow<Reuniao?> get() = _reuniaoSelecionada


    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> get() = _erro


    fun carregarReunioes(salaId: String) {
        viewModelScope.launch {
            service.listarReunioesDaSala(salaId)
                .onSuccess { _reunioes.value = it }
                .onFailure { _erro.value = it.message }
        }
    }


    fun carregarReuniao(salaId: String, reuniaoId: String) {
        viewModelScope.launch {
            service.listarReunioesDaSala(salaId)
                .onSuccess {
                    _reuniaoSelecionada.value = it.find { r -> r.id == reuniaoId }
                }
                .onFailure { _erro.value = it.message }
        }
    }


    fun criarReuniao(reuniao: Reuniao) {
        viewModelScope.launch {
            service.criarReuniao(reuniao)
                .onFailure { _erro.value = it.message }
        }
    }


    fun atualizarReuniao(reuniao: Reuniao) {
        viewModelScope.launch {
            service.atualizarReuniao(reuniao)
                .onFailure { _erro.value = it.message }
        }
    }


    fun removerReuniao(salaId: String, reuniaoId: String) {
        viewModelScope.launch {
            service.excluirReuniao(salaId, reuniaoId)
                .onFailure { _erro.value = it.message }
        }
    }


    fun carregarReunioesDoUsuario(userId: String) {
        viewModelScope.launch {
            service.listarReunioesDoUsuario(userId)
                .onSuccess { _reunioes.value = it }
                .onFailure { _erro.value = it.message }
        }
    }


    fun adicionarMembro(reuniao: Reuniao, membroId: String, membroEmail: String) {
        viewModelScope.launch {
            val membro = com.fatec.salafacil.model.reuniao.membro.MembroReuniao(
                userId = membroId,
                email = membroEmail,
                role = "PARTICIPANTE"
            )
            service.adicionarMembro(reuniao, membro)
                .onFailure { _erro.value = it.message }
        }
    }


    fun removerMembro(reuniao: Reuniao, userId: String) {
        viewModelScope.launch {
            service.removerMembro(reuniao, userId)
                .onFailure { _erro.value = it.message }
        }
    }
}
