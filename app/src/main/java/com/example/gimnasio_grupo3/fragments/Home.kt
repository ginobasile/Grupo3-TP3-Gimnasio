package com.example.gimnasio_grupo3.fragments

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.SharedPreferences
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gimnasio_grupo3.Firebase.FirebaseStorageConnection
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.activities.LoginActivity
import com.example.gimnasio_grupo3.activities.MainActivity
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.sessions.MyPreferences
import com.google.android.material.snackbar.Snackbar

class Home : Fragment() {
    lateinit var v : View
    lateinit var btnProfesores : Button
    lateinit var btnUsuarios : Button
    lateinit var btnLogOut : Button
    lateinit var txtNombreCompleto : TextView
    lateinit var txtTickets : TextView
    lateinit var imgUsuario: ImageView
    private var FirebaseStorageConnection = FirebaseStorageConnection()
    private lateinit var myPreferences: MyPreferences
    private var user: Usuario? = null
    companion object {
        fun newInstance() = Home()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        btnProfesores = v.findViewById(R.id.btnProfesores)
        btnUsuarios = v.findViewById(R.id.btnUsuarios)
        btnLogOut = v.findViewById(R.id.btnLogOut)

        txtNombreCompleto = v.findViewById(R.id.txtNombreApellido)
        txtTickets = v.findViewById(R.id.txtEmail3)
        imgUsuario = v.findViewById(R.id.imgNavUsuario)
        myPreferences = MyPreferences(requireContext())
        user = myPreferences.getUser()



        btnLogOut.setOnClickListener {
            val myPreferences = MyPreferences(requireContext())
            myPreferences.deleteUser()
            val intent = Intent(activity?.applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        val myPreferences = MyPreferences(requireContext())
        val user = myPreferences.getUser()

        if (user != null) {
            setNombreCompleto(user.nombre, user.apellido)
            setTickets(user.ticketsRestantes.toString())
            FirebaseStorageConnection.getImage(imgUsuario,"usuarios/${user.dni}.jpg")
        }

        if (myPreferences.isAdmin() != true) {
            btnProfesores.visibility = View.GONE
            btnUsuarios.visibility = View.GONE
        }

        btnProfesores.setOnClickListener {
            val action = HomeDirections.actionHomePrincipalToProfesoresLista()
            findNavController().navigate(action)
        }

        btnLogOut.setOnClickListener {
            myPreferences.deleteUser()
            val intent = Intent(activity?.applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        btnUsuarios.setOnClickListener() {
            val action = HomeDirections.actionHomePrincipalToUsuariosLista()
            findNavController().navigate(action)
        }

        val cardView = v.findViewById<CardView>(R.id.detallesUsuario)
        cardView.setOnClickListener {
            if (user != null) {
                val action = HomeDirections.actionHomePrincipalToDetalleUsuario(user)
                findNavController().navigate(action)
            }
        }
    }

    fun setNombreCompleto(nombre: String, apellido: String){
        val nombreCompleto = "${apellido}, ${nombre}"
        txtNombreCompleto.text = nombreCompleto.uppercase()
    }


    fun setTickets(tickets: String){
        txtTickets.text = "Tickets: ${tickets}"
    }
}