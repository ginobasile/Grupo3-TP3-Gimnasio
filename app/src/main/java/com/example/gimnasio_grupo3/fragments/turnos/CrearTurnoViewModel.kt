package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearTurnoViewModel : ViewModel() {
    fun crearTurno (nuevoTurno : Turno, callback: (String) -> Unit){

        val retrofit = TurnosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        val call = apiService.createTurno(nuevoTurno)

        call.enqueue(object : Callback<Turno> {
            override fun onResponse(call: Call<Turno>, response: Response<Turno>) {
                if (response.isSuccessful) {
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Turno creado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al crear turno")
                }
            }

            override fun onFailure(call: Call<Turno>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear turno")
            }
        })
    }
}