package com.example.gimnasio_grupo3.sessions
import android.content.Context
import android.content.SharedPreferences
import com.example.gimnasio_grupo3.entities.Usuario


class MyPreferences (context: Context) {
    private val PREF_NAME = "myPreferences"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUser(user: Usuario) {
        val editor = sharedPreferences.edit()
        editor.putString("USER", user.toString())
        editor.apply()
    }

    fun getUser(): String? {
        return sharedPreferences.getString("USER", null)
    }
}