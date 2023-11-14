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