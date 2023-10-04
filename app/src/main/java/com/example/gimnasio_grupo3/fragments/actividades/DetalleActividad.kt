package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gimnasio_grupo3.R

class DetalleActividad : Fragment() {
    lateinit var v : View
    lateinit var txtDesc : TextView
    lateinit var txtNombre : TextView
    lateinit var txtDurac : TextView
    companion object {
        fun newInstance() = DetalleActividad()
    }

    private lateinit var viewModel: DetalleActividadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_actividad, container, false)
        txtDesc = v.findViewById(R.id.txtDescrip)
        txtNombre = v.findViewById(R.id.txtName)
        txtDurac = v.findViewById(R.id.txtDuration)
        return v
    }


    override fun onStart() {
        super.onStart()
        val actividad = DetalleActividadArgs.fromBundle(requireArguments()).actividad
        txtNombre.text = actividad.name
        txtDurac.text = actividad.duration



    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleActividadViewModel::class.java)
        // TODO: Use the ViewModel
    }

}