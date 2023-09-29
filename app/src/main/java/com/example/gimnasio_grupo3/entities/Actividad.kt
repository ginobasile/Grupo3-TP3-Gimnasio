package com.example.gimnasio_grupo3.entities


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actividad(
    var id: Int,
    var name: String,
    var duration: String,
    var description: String,
    var imgUrl : String
): Parcelable
