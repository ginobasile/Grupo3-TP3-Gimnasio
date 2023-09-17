package com.example.gimnasio_grupo3.fragments.profesores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gimnasio_grupo3.R

class DetalleProfesor : Fragment() {

    companion object {
        fun newInstance() = DetalleProfesor()
    }

    private lateinit var viewModel: DetalleProfesorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle_profesor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleProfesorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}