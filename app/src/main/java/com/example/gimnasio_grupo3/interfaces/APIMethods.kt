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
    // PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES
    // Create
    @POST("paquetes")
    fun createPaquete(@Body paquete: Paquete): Call<Paquete>

    // Read
    @GET("paquetes")
    fun getPaquetes(): Call<List<Paquete>>

    // Update
    @PUT("paquetes/{id}")
    fun updatePaquete(@Path("id") id: String, @Body paquete: Paquete): Call<Paquete>

    // Delete
    @DELETE("paquetes/{id}")
    fun deletePaquete(@Path("id") id: String): Call<Void>

    // ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES
    // Create
    @POST("Actividades")
    fun createActividad(@Body paquete: Actividad): Call<Actividad>

    // Read
    @GET("Actividades")
    fun getActividad(): Call<List<Actividad>>

    // Update
    @PUT("Actividades/{id}")
    fun updateActividad(@Path("id") id: String, @Body paquete: Actividad): Call<Actividad>

    // Delete
    @DELETE("Actividades/{id}")
    fun deleteActividad(@Path("id") id: String): Call<Void>

    // PROFESORES-PROFESORES-PROFESORES-PROFESORES-PROFESORES
    // Create

    // Read

    // Update

    // Delete

    // TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS
    // Create

    // Read

    // Update

    // Delete

    // USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS
    // Create

    // Read

    // Update

    // Delete


}