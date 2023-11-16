package com.example.gimnasio_grupo3.fragments.usuarios

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

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

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

    fun actualizarUsuario(usuario: Usuario, callback: (String) -> Unit) {

        val call = apiService.updateUsuario(usuario.id.toString(), usuario)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    callback("Usuario actualizado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al actualizar Usuario")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al actualizar Usuario")
            }
        })
    }

}