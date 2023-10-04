package com.example.gimnasio_grupo3.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Paquete(
    var id: Int,
    var nombre: String,
    var cantTickets: Int,
    var precio: Int
) : Parcelable {

    constructor(nombre: String, cantTickets: Int, precio: Int) : this(0, nombre, cantTickets, precio)
}

