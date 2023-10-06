package com.example.gimnasio_grupo3.fragments.paquetes

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.PaquetesProvider
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaquetesListaViewModel : ViewModel() {

    fun obtenerPaquetes(callback: (List<Paquete>?) -> Unit) {
        val retrofit = PaquetesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getPaquetes()

        call.enqueue(object : Callback<List<Paquete>> {
            override fun onResponse(call: Call<List<Paquete>>, response: Response<List<Paquete>>) {
                if (response.isSuccessful) {
                    val paquetesList = response.body()
                    callback(paquetesList)
                } else {
                    // Maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Paquete>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }
}