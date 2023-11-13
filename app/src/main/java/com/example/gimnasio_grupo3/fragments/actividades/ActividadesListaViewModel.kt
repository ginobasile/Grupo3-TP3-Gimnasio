package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActividadesListaViewModel : ViewModel() {

    val retrofit = ActividadesProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    var actividadesCargadas : MutableLiveData<List<Actividad>> =  MutableLiveData<List<Actividad>>()
    var state : MutableLiveData<String> =  MutableLiveData<String>()



    fun cargarActividades() {
        val call = apiService.getActividad()

        if (actividadesCargadas.value == null ){

            state.value = "Loading"

            call.enqueue(object : Callback<List<Actividad>> {
                override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
                    if (response.isSuccessful) {
                        state.value = "Success"
                        actividadesCargadas.value = response.body()
                    } else {
                        // La llamada a la API no fue exitosa
                        // Puedes manejar errores aquí
                        state.value = "Error_1"
                    }
                }

                override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                    // Error en la llamada a la API
                    // Puedes manejar errores de red u otros aquí
                }
            })
        }
    }

    fun recargarActividades() {

        state.value = "Loading"

        val call = apiService.getActividad()

        call.enqueue(object : Callback<List<Actividad>> {
            override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
                if (response.isSuccessful) {
                    state.value = "Success"
                    actividadesCargadas.value = response.body()
                } else {
                    // La llamada a la API no fue exitosa
                    // Puedes manejar errores aquí
                }
            }

            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                // Error en la llamada a la API
                // Puedes manejar errores de red u otros aquí
            }
        })
    }


// Deprecated

//    fun obtenerActividades(callback: (List<Actividad>?) -> Unit) {
//
//        val call = apiService.getActividad()
//
//        call.enqueue(object : Callback<List<Actividad>> {
//            override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
//                if (response.isSuccessful) {
//                    val actividadesLista = response.body()
//                    callback(actividadesLista)
//                }
//                else {
//                    // La llamada no fue exitosa, maneja los errores aquí
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
//                // Maneja errores de conexión aquí
//                callback(null)
//            }
//        })
//    }


}