package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosPersonasProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MisTurnosViewModel : ViewModel() {

    fun obtenerActividades(callback: (List<Actividad>?) -> Unit) {
        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getActividad()

        call.enqueue(object : Callback<List<Actividad>> {
            override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
                if (response.isSuccessful) {
                    val actividadesLista = response.body()
                    callback(actividadesLista)
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }

    fun obtenerTurnos(callback: (List<Turno>?) -> Unit) {
        val retrofit = TurnosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getTurno()

        call.enqueue(object : Callback<List<Turno>> {
            override fun onResponse(call: Call<List<Turno>>, response: Response<List<Turno>>) {
                if (response.isSuccessful) {
                    val turnoLista = response.body()
                    callback(turnoLista)
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Turno>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }

    fun obtenerTurnoPersonas(callback: (List<TurnoPersona>?) -> Unit) {
        val retrofit = TurnosPersonasProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getTurnosPersonas()

        call.enqueue(object : Callback<List<TurnoPersona>> {
            override fun onResponse(call: Call<List<TurnoPersona>>, response: Response<List<TurnoPersona>>) {
                if (response.isSuccessful) {
                    val turnosPersonas = response.body()
                    callback(turnosPersonas)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<TurnoPersona>>, t: Throwable) {
                callback(null)
            }
        })
    }
}