package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TurnosListaViewModel : ViewModel() {
    fun obtenerTurnos(callback: (List<Turno>?) -> Unit) {
        val retrofit = TurnosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getTurno()

        call.enqueue(object : Callback<List<Turno>> {
            override fun onResponse(call: Call<List<Turno>>, response: Response<List<Turno>>) {
                if (response.isSuccessful) {
                    val turnoLista = response.body()
                    callback(turnoLista)
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Turno>>, t: Throwable) {
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
