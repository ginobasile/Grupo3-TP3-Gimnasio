package com.example.gimnasio_grupo3.interfaces

import com.example.gimnasio_grupo3.entities.Paquete
import retrofit2.Call
import retrofit2.http.GET

interface PaqueteAPI {
    @GET("paquetes")
    fun getPaquetes(): Call<List<Paquete>>
}