package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gimnasio_grupo3.R

class DetalleTurno : Fragment() {
    lateinit var v : View
    companion object {
        fun newInstance() = DetalleTurno()
    }

    private lateinit var viewModel: DetalleTurnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_turno, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleTurnoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}