package com.example.gimnasio_grupo3.fragments.loginFragments

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearUsuarioViewModel : ViewModel() {
    fun crearUsuario (nuevoUsuario : Usuario, v: View, callback: (String) -> Unit){

        val retrofit = UsuariosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        val call = apiService.createUsuario(nuevoUsuario)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    Snackbar.make(v, response.toString(), Snackbar.LENGTH_LONG).show()
                    Log.d("respomse", response.toString())
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Usuario creado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    Log.d("errore", response.toString())
                    callback("Error al crear el Usuario")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear el Usuario")
            }
        })
    }
}