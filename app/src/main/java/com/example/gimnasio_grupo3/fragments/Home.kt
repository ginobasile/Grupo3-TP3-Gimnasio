package com.example.gimnasio_grupo3.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.gimnasio_grupo3.R
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class Home : Fragment() {
    lateinit var v : View
    lateinit var btnProfesores : Button
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

        btnProfesores.setOnClickListener() {
            val action = HomeDirections.actionHomePrincipalToProfesoresLista()
            findNavController().navigate(action)


        }
    }

    // [Tomas] Agregado para probar Profesores - END

}