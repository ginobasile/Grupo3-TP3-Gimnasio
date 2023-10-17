package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import com.example.gimnasio_grupo3.entities.Actividad
import androidx.navigation.findNavController

class ActividadesLista : Fragment() {

    lateinit var v: View
    lateinit var recyclerActividades: RecyclerView
    lateinit var adapter: ActividadAdapter
    lateinit var actividadesList: MutableList<Actividad>
    private lateinit var btnCrearActividad: Button
    private lateinit var btnBack: Button
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
        recyclerActividades.layoutManager = LinearLayoutManager(requireContext())
        btnCrearActividad = v.findViewById(R.id.buttonActividades)
        btnBack = v.findViewById(R.id.button7)

        btnCrearActividad.setOnClickListener {
            val action = ActividadesListaDirections.actionActividadesListaToCrearActividad()

            findNavController().navigate(action)
        }

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActividadesListaViewModel::class.java)

        viewModel.obtenerActividades { actividadesList ->
            if (actividadesList != null) {
                adapter = ActividadAdapter(actividadesList.toMutableList()) { actividad ->
                    val action =
                        ActividadesListaDirections.actionActividadesListaToDetalleActividad(
                            actividad
                        )
                    findNavController().navigate(action)
                }
                recyclerActividades.adapter = adapter
            }
        }
    }
}