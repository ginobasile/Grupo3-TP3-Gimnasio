package com.example.gimnasio_grupo3.entities

data class Usuario(
    var id: Int,
    var nombre: String,
    var apellido: String,
    var mail: String,
    var password: String,
    var altura: Int,
    var peso: Int,
    var edad: Int,
    var contacto: String,
    var administrador: String,
    var dni: Int,
    var ticketsRestantes: Int

)
