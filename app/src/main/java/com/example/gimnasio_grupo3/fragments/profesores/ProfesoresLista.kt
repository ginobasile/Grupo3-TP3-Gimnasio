package com.example.gimnasio_grupo3.fragments.profesores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gimnasio_grupo3.R

class ProfesoresLista : Fragment() {

    companion object {
        fun newInstance() = ProfesoresLista()
    }

    private lateinit var viewModel: ProfesoresListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profesores_lista, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfesoresListaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}