package com.example.gimnasio_grupo3.entities


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actividad(
    var id: Int,
    var name: String,
    var duration: Int,

): Parcelable {
    constructor(name: String, duration: Int) : this(0, name, duration)
}

