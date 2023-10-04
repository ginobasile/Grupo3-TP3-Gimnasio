package com.example.gimnasio_grupo3.interfaces

import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Paquete
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIMethods {
    // Create: Agregar un nuevo paquete
    @POST("paquetes")
    fun createPaquete(@Body paquete: Paquete): Call<Paquete>

    // Read: Obtener todos los paquetes
    @GET("paquetes")
    fun getPaquetes(): Call<List<Paquete>>

    // Update: Actualizar un paquete existente
    @PUT("paquetes/{id}")
    fun updatePaquete(@Path("id") id: String, @Body paquete: Paquete): Call<Paquete>

    // Delete: Eliminar un paquete
    @DELETE("paquetes/{id}")
    fun deletePaquete(@Path("id") id: String): Call<Void>

    @GET("actividades")
    fun getActividades(): Call<List<Actividad>>

}