package com.cibertec.reztrov01.models

data class Reserva(
    val nomUsuario : String,
    val restaurante : String,
    val fecha : String,
    val horario : String,
    val mesa : String,
    val dni: String,
    val qr: String,
    val id: Int
)