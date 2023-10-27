package com.example.gimnasio_grupo3.fragments.loginFragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.activities.LoginActivity
import com.example.gimnasio_grupo3.activities.MainActivity
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.sessions.MyPreferences
import com.google.android.material.snackbar.Snackbar

class Login : Fragment() {
    lateinit var btnNavigate : Button
    lateinit var inputUserEmail : EditText
    lateinit var inputUserPass : EditText
    lateinit var v : View
    private val PREF_NAME = "myPreferences"

    var userList: List<Usuario>  = listOf(

        Usuario(1, "Admin", "Admin", "admin@admin.com", "admin", 170, 70, 25, "123456", true, 1234, 0),
        Usuario(2, "Juan", "Ramirez", "jose@jose.com", "123", 170, 70, 25, "123456", false, 1234, 15),



    )
    companion object {
        fun newInstance() = Login()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        btnNavigate = v.findViewById(R.id.btnNavigate)
        inputUserEmail = v.findViewById(R.id.inputMail)
        inputUserPass = v.findViewById(R.id.inputPass)
        return v
    }

    override fun onStart(){
        super.onStart()


        btnNavigate.setOnClickListener {
            val inputUser = inputUserEmail.text.toString()
            val inputPass = inputUserPass.text.toString()

            if (inputUser.isEmpty()) {
                Snackbar.make(v, "El campo de correo está vacío", Snackbar.LENGTH_SHORT).show()
            } else if (inputPass.isEmpty()) {
                Snackbar.make(v, "El campo de contraseña está vacío", Snackbar.LENGTH_SHORT).show()
            } else {
                val user = userList.find { it.mail == inputUser }
                if (user != null) {
                    if (user.password == inputPass) {
                        //Gino Agrego nuevo
                        val myPreferences = MyPreferences(requireContext())
                        myPreferences.setUser(user)
                        val intent = Intent(activity?.applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(v, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(v, "El usuario no existe", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}