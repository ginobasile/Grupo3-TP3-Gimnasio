package com.example.gimnasio_grupo3.fragments.paquetes

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.PaquetesProvider
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearPaqueteViewModel : ViewModel() {
    fun crearPaquete (nuevoPaquete : Paquete, callback: (String) -> Unit){

        val retrofit = PaquetesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        val call = apiService.createPaquete(nuevoPaquete)

        call.enqueue(object : Callback<Paquete> {
            override fun onResponse(call: Call<Paquete>, response: Response<Paquete>) {
                if (response.isSuccessful) {
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Paquete creado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al crear el paquete")
                }
            }

            override fun onFailure(call: Call<Paquete>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear el paquete")
            }
        })
    }
}