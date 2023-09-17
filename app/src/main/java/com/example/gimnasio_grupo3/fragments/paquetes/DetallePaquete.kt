package com.example.gimnasio_grupo3.fragments.paquetes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gimnasio_grupo3.R

class DetallePaquete : Fragment() {
    lateinit var v : View
    companion object {
        fun newInstance() = DetallePaquete()
    }

    private lateinit var viewModel: DetallePaqueteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_paquete, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetallePaqueteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}