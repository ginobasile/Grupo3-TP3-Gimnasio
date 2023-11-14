package com.example.gimnasio_grupo3.fragments.turnos

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosPersonasProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.RetroFitProviders.UsuariosProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TurnosListaViewModel : ViewModel() {

    var turnosCargados : MutableLiveData<List<Turno>> = MutableLiveData<List<Turno>>()
    var actividadesCargadas: MutableLiveData<List<Actividad>> = MutableLiveData<List<Actividad>>()
    var profesoresCargados: MutableLiveData<List<Profesor>> = MutableLiveData<List<Profesor>>()

    var state : MutableLiveData<String> = MutableLiveData<String>()
    fun obtenerTurnos() { // los turnos deben cargar al final para evitar conflictos
        state.value = "Loading"
        cargarActividades()
    }

    fun recargarTurnos() {
       obtenerTurnos()
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

    private fun cargarActividades() {
        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getActividad()

        call.enqueue(object : Callback<List<Actividad>> {
            override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
                if (response.isSuccessful) {
                    actividadesCargadas.value = response.body()
                    Log.d("Carga", "Actividades OK")
                    cargarProfesores()
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    state.value = "Error_1"
                }
            }
            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })

    }

    private fun cargarProfesores() {
        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getProfesores()

        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                if (response.isSuccessful) {
                    profesoresCargados.value = response.body()
                    Log.d("Carga", "Profesores OK")
                    cargarTurnos()
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    state.value = "Error_1"
                }
            }
            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }

    private fun cargarTurnos() {
        val retrofit = TurnosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getTurno()

        call.enqueue(object : Callback<List<Turno>> {
            override fun onResponse(call: Call<List<Turno>>, response: Response<List<Turno>>) {
                if (response.isSuccessful) {
                    turnosCargados.value = response.body()
                    Log.d("Carga", "Turnos OK")
                    state.value = "Success"
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    state.value = "Error_1"
                }
            }

            override fun onFailure(call: Call<List<Turno>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }
}
