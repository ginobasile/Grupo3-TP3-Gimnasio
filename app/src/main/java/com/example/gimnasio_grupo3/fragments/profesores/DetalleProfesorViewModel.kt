package com.example.gimnasio_grupo3.fragments.profesores

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleProfesorViewModel : ViewModel() {
    val retrofit = ProfesoresProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    fun actualizarProfesor(profesorActualizada: Profesor, callback: (String) -> Unit) {
        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.updateProfesor(profesorActualizada.id.toString(), profesorActualizada)

        call.enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    callback("Profesor actualizada exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al actualizar Profesor")
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al actualizar Profesor")
            }
        })
    }

    fun eliminarProfesor(profesor: Profesor, callback: (String) -> Unit) {
        val call = apiService.deleteProfesor(profesor.id.toString())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa
                    callback("Profesor eliminada exitosamente")
                } else {
                    // La eliminación no fue exitosa, maneja los errores aquí
                    callback("Error al eliminar Profesor")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al eliminar Profesor")
            }
        })
    }
}