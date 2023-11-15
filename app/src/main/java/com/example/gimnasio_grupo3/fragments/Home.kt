package com.example.gimnasio_grupo3.fragments

import android.app.AlertDialog
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
import retrofit2.Call

class Home : Fragment() {
    lateinit var v : View
    lateinit var btnProfesores : Button
    lateinit var btnUsuarios : Button
    lateinit var btnLogOut : Button
    lateinit var txtNombreCompleto : TextView
    lateinit var imgUsuario: ImageView
    private var FirebaseStorageConnection = FirebaseStorageConnection()
    lateinit var txtInfo : TextView
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
        imgUsuario = v.findViewById(R.id.imgNavUsuario)
        txtInfo = v.findViewById(R.id.txtEmail3)
        myPreferences = MyPreferences(requireContext())
        user = myPreferences.getUser()

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
        } else {
            txtInfo.text = "Cuenta de administrador"
            txtInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            val drawable = requireContext().getDrawable(R.drawable.setting)
            txtInfo.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }

        btnProfesores.setOnClickListener {
            val action = HomeDirections.actionHomePrincipalToProfesoresLista()
            findNavController().navigate(action)
        }

        btnLogOut.setOnClickListener {
            confirmAction() { confirmed ->
                if(confirmed){
                    myPreferences.deleteUser()
                    val intent = Intent(activity?.applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
                }
            }
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

    private fun setNombreCompleto(nombre: String, apellido: String) {
        val nombreCompleto = "${apellido}, ${nombre}"
        txtNombreCompleto.text = nombreCompleto.uppercase()
    }

    private fun setTickets(tickets: String){
        txtInfo.text = "Tickets: ${tickets}"
    }

    private fun confirmAction(callback: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cerrar Sesión")
        builder.setMessage("¿Estás seguro de que deseas Cerrar Sesión?")
        builder.setPositiveButton("Cerrar Sesión") { dialog, _ ->
            callback(true) // Llama al callback con 'true' cuando el usuario confirma
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            callback(false) // Llama al callback con 'false' cuando el usuario cancela
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}