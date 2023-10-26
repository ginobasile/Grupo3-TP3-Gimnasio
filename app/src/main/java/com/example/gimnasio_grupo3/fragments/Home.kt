package com.example.gimnasio_grupo3.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.SharedPreferences
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.gimnasio_grupo3.R
import com.google.android.material.snackbar.Snackbar

class Home : Fragment() {
    lateinit var v : View
    lateinit var btnProfesores : Button
    lateinit var btnUsuarios : Button
    private var imagesList = mutableListOf<Int>()
    private val PREF_NAME = "myPreferences"

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

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }


    // [Tomas] Agregado para testear Profesores - START
    override fun onStart() {
        super.onStart()
        val sharedPref = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val storedUser = sharedPref.getString("USER", "")
        Snackbar.make(v, storedUser ?: "Mensaje predeterminado si no hay valor", Snackbar.LENGTH_SHORT).show()

        btnProfesores.setOnClickListener() {
            val action = HomeDirections.actionHomePrincipalToProfesoresLista()
            findNavController().navigate(action)
        }

        btnUsuarios.setOnClickListener() {
            val action = HomeDirections.actionHomePrincipalToUsuariosLista()
            findNavController().navigate(action)
        }
    }
    // [Tomas] Agregado para probar Profesores - END

    private fun addToList(image: Int){
        imagesList.add(image)
    }

    private fun postToList(){
        for(i in 1..5){
            addToList(R.mipmap.ic_launcher_round)
        }
    }
}