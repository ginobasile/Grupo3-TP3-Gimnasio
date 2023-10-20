package com.example.gimnasio_grupo3.fragments.profesores

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfesoresListaViewModel : ViewModel() {

    fun obtenerProfesores(callback: (List<Profesor>?) -> Unit) {
        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getProfesores()

        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                if (response.isSuccessful) {
                    val profesoresLista = response.body()
                    callback(profesoresLista)
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }
}