package com.example.gimnasio_grupo3.fragments.usuarios

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleUsuarioViewModel : ViewModel() {

    val retrofit = UsuariosProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)


    fun eliminarUsuario(usuario: Usuario, callback: (String) -> Unit) {

        val call = apiService.deleteUsuarios(usuario.id.toString())

        Log.d("API",call.toString())

        call.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                Log.d("API",response.toString())

                if (response.isSuccessful) {

                    callback("Usuario eliminado exitosamente")
                } else {

                    callback("Error al eliminar Usuario")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al eliminar Usuario")
            }
        })
    }

}