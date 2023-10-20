package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosProvider
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import com.example.gimnasio_grupo3.adapters.ProfesorAdapter
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.google.android.material.snackbar.Snackbar

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearTurnoViewModel : ViewModel() {

    fun obtenerProfesores(callback: (List<Profesor>?) -> Unit) {
        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getProfesores()

        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                if (response.isSuccessful) {
                    val profesoresLista = response.body()
                    callback(profesoresLista)
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }

    fun obtenerActividades(callback: (List<Actividad>?) -> Unit) {
        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getActividad()

        call.enqueue(object : Callback<List<Actividad>> {
            override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
                if (response.isSuccessful) {
                    val actividadesLista = response.body()
                    callback(actividadesLista)
                }
                else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }
    fun crearTurno (nuevoTurno : Turno, callback: (String) -> Unit){

        val retrofit = TurnosProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)


        val call = apiService.createTurno(nuevoTurno)

        call.enqueue(object : Callback<Turno> {
            override fun onResponse(call: Call<Turno>, response: Response<Turno>) {
                if (response.isSuccessful) {
                    // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                    callback("Turno creado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al crear turno")
                }
            }

            override fun onFailure(call: Call<Turno>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al crear turno")
            }
        })
    }



}