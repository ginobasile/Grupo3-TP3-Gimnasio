package com.example.gimnasio_grupo3.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var administrador: Boolean,
    var dni: Int,
    var ticketsRestantes: Int

) : Parcelable {
    constructor(nombre: String, apellido: String, mail: String, password: String, altura: Int, peso: Int, edad: Int, contacto: String, administrador: Boolean, dni: Int, ticketsRestantes: Int):
            this(0, nombre, apellido, mail, password, altura, peso, edad, contacto, administrador, dni, ticketsRestantes)
}
