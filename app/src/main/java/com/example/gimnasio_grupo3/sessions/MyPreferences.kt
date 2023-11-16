package com.example.gimnasio_grupo3.sessions

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.gimnasio_grupo3.RetroFitProviders.TurnosPersonasProvider
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPreferences(context: Context) {
    private val PREF_NAME = "myPreferences"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()
    private var turnosDelUsuario: List<TurnoPersona>? = null


    init {
        obtenerTurnosDelUsuario()
        Log.d("Sacar2", turnosDelUsuario.toString())
    }
    fun setUser(user: Usuario) {
        val editor = sharedPreferences.edit()
        val userJson = gson.toJson(user)
        editor.putString("USER", userJson)
        editor.apply()
    }

    fun deleteUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun getUser(): Usuario? {
        val userJson = sharedPreferences.getString("USER", null)
        return gson.fromJson(userJson, Usuario::class.java)
    }

    fun isAdmin(): Boolean {
        var isAdmin = false
        val user = getUser()
        if (user != null) {
            if (user.administrador) {
                isAdmin = true
            }
        }

        return isAdmin
    }

    fun poseeElTurno(turno: Turno) : Boolean {
        var posee = false
        val user = getUser()

        if(user != null){
            val turnoASacar = turnosDelUsuario?.find { it.idTurno == turno.id }
            Log.d("SacarTurnoUsuario", turnosDelUsuario.toString())
            Log.d("SacarTurnoORIGINAL", turno.toString())
            if(turnoASacar != null && turnoASacar.idUsuario == user.id){
                posee = true
            }
        }

        return posee
    }

    private fun obtenerTurnosDelUsuario() {
        val user = getUser()
        if (user != null) {
            if(!user.administrador) {
                obtenerTurnoPersonas { turnosList ->
                    if (turnosList != null) {
                        turnosDelUsuario = turnosList.filter {
                            it.idUsuario == (getUser()?.id ?: 0)
                        }
                    }
                }
            }
        }
    }

    private fun obtenerTurnoPersonas(callback: (List<TurnoPersona>?) -> Unit) {
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
}