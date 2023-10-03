package com.example.gimnasio_grupo3.interfaces

import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Paquete
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface PaqueteAPI {
    @GET("paquetes")
    fun getPaquetes(): Call<List<Paquete>>

    @GET("actividades")
    fun getActividades(): Call<List<Actividad>>
}