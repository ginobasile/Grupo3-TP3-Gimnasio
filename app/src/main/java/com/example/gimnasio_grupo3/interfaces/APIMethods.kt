package com.example.gimnasio_grupo3.interfaces

import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIMethods {
    // PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES-PAQUETES
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

    // ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES-ACTIVIDADES
    // Create
    @POST("actividades")
    fun createActividad(@Body actividad: Actividad): Call<Actividad>

    // Read
    @GET("actividades")
    fun getActividad(): Call<List<Actividad>>

    // Update
    @PUT("actividades/{id}")
    fun updateActividad(@Path("id") id: String, @Body actividad: Actividad): Call<Actividad>

    // Delete
    @DELETE("actividades/{id}")
    fun deleteActividad(@Path("id") id: String): Call<Void>

    // PROFESORES-PROFESORES-PROFESORES-PROFESORES-PROFESORES-PROFESORES-PROFESORES-PROFESORES-PROFESORES
    // Create
    @POST("profesores")
    fun createProfesor(@Body profesor: Profesor): Call<Profesor>

    // Read
    @GET("profesores")
    fun getProfesores(): Call<List<Profesor>>

    // Update
    @PUT("profesores/{id}")
    fun updateProfesor(@Path("id") id: String, @Body profesor: Profesor): Call<Profesor>

    // Delete
    @DELETE("profesores/{id}")
    fun deleteProfesor(@Path("id") id: String): Call<Void>

    // TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS-TURNOS
    // Create
    @POST("turnos")
    fun createTurno(@Body turno: Turno): Call<Turno>

    // Read
    @GET("turnos")
    fun getTurno(): Call<List<Turno>>

    // Update
    @PUT("turnos/{id}")
    fun updateTurno(@Path("id") id: String, @Body turno: Turno): Call<Turno>

    // Delete
    @DELETE("turnos/{id}")
    fun deleteTurno(@Path("id") id: String): Call<Void>

    // USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS-USUARIOS
    // Create
    @POST("Usuarios")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    // Read
    @GET("Usuarios")
    fun getUsuarios(): Call<List<Usuario>>

    // Update
    @PUT("Usuarios/{id}")
    fun updateUsuario(@Path("id") id: String, @Body usuario: Usuario): Call<Usuario>

    // Delete
    @DELETE("Usuarios/{id}")
    fun deleteUsuarios(@Path("id") id: String): Call<Void>
}