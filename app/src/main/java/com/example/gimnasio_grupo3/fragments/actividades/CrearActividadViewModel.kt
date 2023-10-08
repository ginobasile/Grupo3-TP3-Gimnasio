package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearActividadViewModel : ViewModel() {
    fun crearActividad (nuevoActividad : Actividad, callback: (String) -> Unit){

        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        val call = apiService.createActividad(nuevoActividad)

        call.enqueue(object : Callback<Actividad> {
            override fun onResponse(call: Call<Actividad>, response: Response<Actividad>) {
                if (response.isSuccessful) {
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Actividad creada exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al crear la actividad")
                }
            }

            override fun onFailure(call: Call<Actividad>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear la actividad")
            }
        })
    }
}