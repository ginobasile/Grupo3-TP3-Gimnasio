package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosPersonasProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MisTurnosViewModel : ViewModel() {
    val retrofit = TurnosPersonasProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)
    fun obtenerTurnoPersonas(callback: (List<TurnoPersona>?) -> Unit) {
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

    fun eliminarTurnoPersona(turnoPersona: TurnoPersona, callback: (String) -> Unit) {
        val call = apiService.deleteTurnoPersona(turnoPersona.id.toString())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa
                    callback("Tu turno ha sido cancelado exitosamente")
                } else {
                    // La eliminación no fue exitosa, maneja los errores aquí
                    callback("Error al cancelar Turno")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al cancelar Turno")
            }
        })
    }
}