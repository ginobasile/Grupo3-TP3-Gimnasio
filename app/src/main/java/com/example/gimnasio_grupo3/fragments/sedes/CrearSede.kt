package com.example.gimnasio_grupo3.fragments.sedes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gimnasio_grupo3.R

class CrearSede : Fragment() {
    lateinit var v : View
    companion object {
        fun newInstance() = CrearSede()
    }

    private lateinit var viewModel: CrearSedeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_sede, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearSedeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}