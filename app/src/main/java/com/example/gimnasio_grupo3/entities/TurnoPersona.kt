package com.example.gimnasio_grupo3.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TurnoPersona(
    var id: Int,
    var idUsuario: Int,
    var idTurno: Int,
): Parcelable {
    constructor(idUsuario: Int, idTurno: Int) : this(0, idUsuario, idTurno)
}
