package com.example.gimnasio_grupo3.fragments.usuarios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuariosListaViewModel : ViewModel() {

    var usuariosCargados : MutableLiveData<List<Usuario>> =  MutableLiveData<List<Usuario>>()
    var state : MutableLiveData<String> =  MutableLiveData<String>()

    val retrofit = UsuariosProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    init {
        state.value = "Loading"
    }

    fun cargarUsuarios() {
        val call = apiService.getUsuarios()

        if (usuariosCargados.value == null ){
            state.value = "Loading"
            call.enqueue(object : Callback<List<Usuario>> {
                override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                    if (response.isSuccessful) {
                        state.value = "Success"
                        usuariosCargados.value = response.body()
                    } else {
                        // La llamada a la API no fue exitosa
                        // Puedes manejar errores aquí
                        state.value = "Error_1"
                    }
                }

                override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                    // Error en la llamada a la API
                    // Puedes manejar errores de red u otros aquí
                }
            })
        }
    }

    fun recargarUsuarios() {
        state.value = "Loading"
        val call = apiService.getUsuarios()
        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    state.value = "Success"
                    usuariosCargados.value = response.body()
                } else {
                    // La llamada a la API no fue exitosa
                    // Puedes manejar errores aquí
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                // Error en la llamada a la API
                // Puedes manejar errores de red u otros aquí
            }
        })
    }

}