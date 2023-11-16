package com.example.gimnasio_grupo3.fragments.usuarios

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import kotlinx.coroutines.launch
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
        if (usuariosCargados.value == null ){
            viewModelScope.launch {
                try {
                    val response:Response<List<Usuario>> =  apiService.getUsuariosForCorutines()

                    if (response.isSuccessful) {
                        state.value = "Success"
                        usuariosCargados.value = response.body()
                    } else {
                        state.value = "Error_1"
                    }
                } catch (e: Exception) {
                    state.value = "Error_2"
                }
            }
        }
    }

    fun recargarUsuarios() {
        state.value = "Loading"
        viewModelScope.launch {
            try {
                val response:Response<List<Usuario>> =  apiService.getUsuariosForCorutines()

                if (response.isSuccessful) {
                    state.value = "Success"
                    usuariosCargados.value = response.body()
                } else {
                    state.value = "Error_1"
                }
            } catch (e: Exception) {
                state.value = "Error_2"
            }
        }

    }


}