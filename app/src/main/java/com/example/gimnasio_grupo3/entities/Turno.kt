package com.example.gimnasio_grupo3.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Turno(

    var id: Int,
    var idProfesor: String,
    var idActividad: String,
    var fecha: String,
    var cantPersonasLim: Int,

): Parcelable {
    constructor(idProfesor: String, idActividad: String, fecha: String, cantPersonasLim: Int) : this(0, idProfesor, idActividad, fecha,cantPersonasLim)
}
