package com.example.gimnasio_grupo3.entities


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profesor(
    var id: Int,
    var nombre: String,
    var apellido: String,

    ): Parcelable {
    constructor( nombre: String, apellido: String) : this( 0, nombre, apellido)
}
