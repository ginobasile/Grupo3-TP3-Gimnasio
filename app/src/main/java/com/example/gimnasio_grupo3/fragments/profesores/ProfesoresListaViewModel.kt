package com.example.gimnasio_grupo3.fragments.profesores

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfesoresListaViewModel : ViewModel() {

    val retrofit = ProfesoresProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    var profesoresCargados : MutableLiveData<List<Profesor>> =  MutableLiveData<List<Profesor>>()
    var state : MutableLiveData<String> =  MutableLiveData<String>()

    fun obtenerProfesores() {
        val call = apiService.getProfesores()

        if(profesoresCargados.value == null) {
            state.value = "Loading"
            call.enqueue(object : Callback<List<Profesor>> {
                override fun onResponse(
                    call: Call<List<Profesor>>,
                    response: Response<List<Profesor>>
                ) {
                    if (response.isSuccessful) {
                        state.value = "Success"
                        profesoresCargados.value = response.body()
                    } else {
                        // La llamada no fue exitosa, maneja los errores aquí
                        state.value = "Error_1"
                    }
                }

                override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                    // Maneja errores de conexión aquí
                }
            })
        }
    }

    fun recargarProfesores() {
        state.value = "Loading"
        val call = apiService.getProfesores()

        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(
                call: Call<List<Profesor>>,
                response: Response<List<Profesor>>
            ) {
                if (response.isSuccessful) {
                    state.value = "Success"
                    profesoresCargados.value = response.body()
                } else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    state.value = "Error_1"
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }
}