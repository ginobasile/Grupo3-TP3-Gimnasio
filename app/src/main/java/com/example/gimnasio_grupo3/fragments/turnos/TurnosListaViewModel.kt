package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosPersonasProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
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

    fun crearTurnoPersona (nuevoTurno : TurnoPersona, callback: (String) -> Unit){

        val retrofit = TurnosPersonasProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        val call = apiService.createTurnoPersona(nuevoTurno)

        call.enqueue(object : Callback<TurnoPersona> {
            override fun onResponse(call: Call<TurnoPersona>, response: Response<TurnoPersona>) {
                if (response.isSuccessful) {
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Turno creado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al crear turno")
                }
            }

            override fun onFailure(call: Call<TurnoPersona>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear turno")
            }
        })
    }

    fun obtenerTurnoPersonas(callback: (List<TurnoPersona>?) -> Unit) {
        val retrofit = TurnosPersonasProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getTurnosPersonas()

        call.enqueue(object : Callback<List<TurnoPersona>> {
            override fun onResponse(call: Call<List<TurnoPersona>>, response: Response<List<TurnoPersona>>) {
                if (response.isSuccessful) {
                    val turnosPersonas = response.body()
                    callback(turnosPersonas)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<TurnoPersona>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun obtenerTurnoPersonasParaFecha(idFecha: Int, callback: (Int) -> Unit) {
        obtenerTurnoPersonas { turnosPersonas ->
            if (turnosPersonas != null) {
                // Filtra los TurnoPersona por el id de fecha
                val inscritosEnFecha = turnosPersonas.filter { it.idTurno == idFecha }
                val cantidadDeInscriptos = inscritosEnFecha.size
                callback(cantidadDeInscriptos)
            } else {
                // Manejar el caso en que no se pudo obtener la lista de TurnoPersona
                callback(-1) // Puedes utilizar un valor negativo u otro indicador de error
            }
        }
    }

}
