package com.example.gimnasio_grupo3.sessions

import android.content.Context
import android.content.SharedPreferences
import com.example.gimnasio_grupo3.entities.Usuario
import com.google.gson.Gson

class MyPreferences(context: Context) {
    private val PREF_NAME = "myPreferences"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

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

    fun setUserTickets(tickets: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("TICKETS", tickets)
        editor.apply()
    }
}