package com.example.gimnasio_grupo3.fragments.paquetes

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.PaquetesProvider
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallePaqueteViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val retrofit = PaquetesProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    fun actualizarPaquete(paqueteActualizado: Paquete, callback: (String) -> Unit) {
        val retrofit = PaquetesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.updatePaquete(paqueteActualizado.id.toString(), paqueteActualizado)

        call.enqueue(object : Callback<Paquete> {
            override fun onResponse(call: Call<Paquete>, response: Response<Paquete>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    callback("Paquete actualizado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al actualizar el paquete")
                }
            }

            override fun onFailure(call: Call<Paquete>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al actualizar el paquete")
            }
        })
    }

    fun eliminarPaquete(paquete: Paquete, callback: (String) -> Unit) {
        val call = apiService.deletePaquete(paquete.id.toString())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa
                    callback("Paquete eliminado exitosamente")
                } else {
                    // La eliminación no fue exitosa, maneja los errores aquí
                    callback("Error al eliminar el paquete")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al eliminar el paquete")
            }
        })
    }

}