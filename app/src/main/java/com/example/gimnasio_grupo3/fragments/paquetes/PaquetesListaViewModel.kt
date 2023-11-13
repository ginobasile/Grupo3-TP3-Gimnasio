package com.example.gimnasio_grupo3.fragments.paquetes

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    val retrofit = PaquetesProvider().provideRetrofit()
    val apiService = retrofit.create(APIMethods::class.java)

    val retrofitUsr = UsuariosProvider().provideRetrofit()
    val apiServiceUsr = retrofitUsr.create(APIMethods::class.java)

    var paquetesCargados : MutableLiveData<List<Paquete>> =  MutableLiveData<List<Paquete>>()
    var state : MutableLiveData<String> =  MutableLiveData<String>()


    fun cargarPaquetes() {
        val call = apiService.getPaquetes()

        if (paquetesCargados.value == null ){
            state.value = "Loading"
            call.enqueue(object : Callback<List<Paquete>> {
                override fun onResponse(call: Call<List<Paquete>>, response: Response<List<Paquete>>) {
                    if (response.isSuccessful) {
                        state.value = "Success"
                        paquetesCargados.value = response.body()
                    } else {
                        // La llamada a la API no fue exitosa
                        // Puedes manejar errores aquí
                        state.value = "Error_1"
                    }
                }

                override fun onFailure(call: Call<List<Paquete>>, t: Throwable) {
                    // Error en la llamada a la API
                    // Puedes manejar errores de red u otros aquí
                }
            })
        }
    }

    fun recargarPaquetes() {
        state.value = "Loading"
        val call = apiService.getPaquetes()

        call.enqueue(object : Callback<List<Paquete>> {
            override fun onResponse(call: Call<List<Paquete>>, response: Response<List<Paquete>>) {
                if (response.isSuccessful) {
                    state.value = "Success"
                    paquetesCargados.value = response.body()
                } else {
                    // La llamada a la API no fue exitosa
                    // Puedes manejar errores aquí
                }
            }

            override fun onFailure(call: Call<List<Paquete>>, t: Throwable) {
                // Error en la llamada a la API
                // Puedes manejar errores de red u otros aquí
            }
        })
    }

    fun actualizarTickets(usuario: Usuario, callback: (String) -> Unit) {
        val call = apiServiceUsr.updateUsuario(usuario.id.toString(), usuario)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    callback("Usuario actualizado exitosamente")
                } else {
                    // La actualización no fue exitosa, maneja los errores aquí
                    Log.d("try",response.toString())
                    callback("Error al actualizar Usuario")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback("Error de conexión al actualizar Usuario")
            }
        })
    }

//    fun obtenerPaquetes(callback: (List<Paquete>?) -> Unit) {
//        val call = apiService.getPaquetes()
//
//        call.enqueue(object : Callback<List<Paquete>> {
//            override fun onResponse(call: Call<List<Paquete>>, response: Response<List<Paquete>>) {
//                if (response.isSuccessful) {
//                    val paquetesList = response.body()
//                    callback(paquetesList)
//                } else {
//                    // Maneja los errores aquí
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Paquete>>, t: Throwable) {
//                // Maneja errores de conexión aquí
//                callback(null)
//            }
//        })
//    }


}