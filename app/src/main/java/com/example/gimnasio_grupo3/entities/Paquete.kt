package com.example.gimnasio_grupo3.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Paquete(
    var id : Int,
    var nombre : String,
    var cantTickets : Int,
    var precio : Int,
) : Parcelable
