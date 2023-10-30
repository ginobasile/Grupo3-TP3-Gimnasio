package com.example.gimnasio_grupo3.fragments.loginFragments

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun obtenerUsuarios(callback: (List<Usuario>?) -> Unit) {
        val retrofit = UsuariosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getUsuarios()


        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    val UsuarioList = response.body()
                    callback(UsuarioList)
                } else {
                    // Maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }
}