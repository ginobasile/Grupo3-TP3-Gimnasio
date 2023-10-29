package com.example.gimnasio_grupo3.fragments.paquetes

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.PaquetesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.entities.Usuario
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

    fun actualizarTickets(usuario: Usuario, callback: (String) -> Unit) {
        val retrofit = UsuariosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
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