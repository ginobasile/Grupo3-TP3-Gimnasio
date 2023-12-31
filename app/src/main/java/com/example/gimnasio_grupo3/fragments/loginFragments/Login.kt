package com.example.gimnasio_grupo3.fragments.loginFragments

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.activities.MainActivity
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.sessions.MyPreferences
import com.google.android.material.snackbar.Snackbar

class Login : Fragment() {
    lateinit var btnNavigate : Button
    lateinit var btnNavigate2 : Button
    lateinit var inputUserEmail : EditText
    lateinit var inputUserPass : EditText
    lateinit var v : View
    lateinit var userList: List<Usuario>

    lateinit var instaButton : ImageButton
    lateinit var faceButton : ImageButton
    lateinit var gitButton : ImageButton

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
        btnNavigate2 = v.findViewById(R.id.btnNavigate2)
        inputUserEmail = v.findViewById(R.id.inputMail)
        inputUserPass = v.findViewById(R.id.inputPass)

        instaButton = v.findViewById(R.id.imageButton7)
        faceButton = v.findViewById(R.id.imageButton8)
        gitButton = v.findViewById(R.id.imageButton9)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        instaButton.setOnClickListener {
            val uri = Uri.parse("https://www.instagram.com/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        faceButton.setOnClickListener {
            val uri = Uri.parse("https://www.facebook.com/?locale=es_LA")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        gitButton.setOnClickListener {
            val uri = Uri.parse("https://github.com/ginobasile/Grupo3-TP3-Gimnasio")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        viewModel.obtenerUsuarios { usuarios ->
            userList = usuarios ?: emptyList()

            btnNavigate2.setOnClickListener {
                val action = LoginDirections.actionLoginToCrearUsuario(userList.map { it.mail }.toTypedArray(), userList.map { it.dni }.toIntArray())
                findNavController().navigate(action)
            }
        }

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

}