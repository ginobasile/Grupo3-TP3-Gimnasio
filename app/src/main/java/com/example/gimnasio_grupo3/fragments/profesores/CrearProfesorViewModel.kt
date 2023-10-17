package com.example.gimnasio_grupo3.fragments.profesores

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearProfesorViewModel : ViewModel() {
    fun crearProfesor (nuevoProfesor : Profesor, callback: (String) -> Unit){

        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        val call = apiService.createProfesor(nuevoProfesor)

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                if (response.isSuccessful) {
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Profesor creada exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al crear la profesor")
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear la profesor")
            }
        })
    }
}