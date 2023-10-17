package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleTurnoViewModel : ViewModel() {
    val retrofit = TurnosProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    fun actualizarTurno(turnoActualizado: Turno, callback: (String) -> Unit) {
        val retrofit = TurnosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.updateTurno(turnoActualizado.id.toString(), turnoActualizado)

        call.enqueue(object : Callback<Turno> {
            override fun onResponse(call: Call<Turno>, response: Response<Turno>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    callback("Turno actualizado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al actualizar el turno")
                }
            }

            override fun onFailure(call: Call<Turno>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al actualizar turno")
            }
        })
    }

    fun eliminarTurno(turno: Turno, callback: (String) -> Unit) {
        val call = apiService.deleteTurno(turno.id.toString())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa
                    callback("Turno eliminado exitosamente")
                } else {
                    // La eliminación no fue exitosa, maneja los errores aquí
                    callback("Error al eliminar turno")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al eliminar turno")
            }
        })
    }
}