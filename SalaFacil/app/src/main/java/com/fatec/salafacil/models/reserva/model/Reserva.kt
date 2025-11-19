package com.fatec.salafacil.models.reserva.model

import com.fatec.salafacil.models.reserva.enum.StatusReserva
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class Reserva {
    private val id: UUID = UUID.randomUUID()
    private var idUsuario: UUID
    private var idSala: UUID
    private var data: LocalDate
    private var horaInicio: LocalDateTime
    private var horaFim: LocalDateTime
    private var statusReserva: StatusReserva
    private val createdAt: LocalDateTime = LocalDateTime.now()

    constructor(
        idUsuario: UUID,
        idSala: UUID,
        data: LocalDate,
        horaInicio: LocalDateTime,
        horaFim: LocalDateTime,
        statusReserva: StatusReserva
    ) {
        this.idUsuario = idUsuario
        this.idSala = idSala
        this.data = data
        this.horaInicio = horaInicio
        this.horaFim = horaFim
        this.statusReserva = statusReserva
    }
}