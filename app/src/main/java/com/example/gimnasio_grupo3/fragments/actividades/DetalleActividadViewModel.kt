package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleActividadViewModel : ViewModel() {
    val retrofit = ActividadesProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    fun actualizarActividad(actividadActualizada: Actividad, callback: (String) -> Unit) {
        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.updateActividad(actividadActualizada.id.toString(), actividadActualizada)

        call.enqueue(object : Callback<Actividad> {
            override fun onResponse(call: Call<Actividad>, response: Response<Actividad>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    callback("Actividad actualizada exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    callback("Error al actualizar Actividad")
                }
            }

            override fun onFailure(call: Call<Actividad>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al actualizar Actividad")
            }
        })
    }

    fun eliminarActividad(actividad: Actividad, callback: (String) -> Unit) {
        val call = apiService.deleteActividad(actividad.id.toString())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa
                    callback("Actividad eliminada exitosamente")
                } else {
                    // La eliminación no fue exitosa, maneja los errores aquí
                    callback("Error al eliminar Actividad")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al eliminar Actividad")
            }
        })
    }
}