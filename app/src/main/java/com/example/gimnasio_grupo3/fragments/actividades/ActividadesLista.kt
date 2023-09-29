package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import com.example.gimnasio_grupo3.entities.ActividadesRepository
import com.google.android.material.snackbar.Snackbar

class ActividadesLista : Fragment() {

    lateinit var v : View
    lateinit var recyclerActividades : RecyclerView
    var repoActividades: ActividadesRepository = ActividadesRepository()
    lateinit var adapter : ActividadAdapter

    companion object {
        fun newInstance() = ActividadesLista()
    }

    private lateinit var viewModel: ActividadesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_actividades_lista, container, false)
        recyclerActividades = v.findViewById(R.id.reciclerActividades)
        return v
    }

    override fun onStart() {
        super.onStart()
        adapter = ActividadAdapter(repoActividades.actividades){ position ->
            val action = ActividadesListaDirections.actionActividadesListaToDetalleActividad(repoActividades.actividades[position])

            findNavController().navigate(action)
        }
        recyclerActividades.layoutManager = LinearLayoutManager(context)
        recyclerActividades.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActividadesListaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}