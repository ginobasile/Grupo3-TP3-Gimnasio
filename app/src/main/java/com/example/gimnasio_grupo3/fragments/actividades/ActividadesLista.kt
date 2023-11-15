package com.example.gimnasio_grupo3.fragments.actividades

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.sessions.MyPreferences

class ActividadesLista : Fragment() {

    lateinit var v: View
    lateinit var recyclerActividades: RecyclerView
    lateinit var adapter: ActividadAdapter
    private lateinit var btnCrearActividad: Button
    private lateinit var btnBack: Button
    private lateinit var myPreferences: MyPreferences
    lateinit var shimmerActividad: LinearLayoutCompat

    lateinit var swipe: SwipeRefreshLayout

    private var user: Usuario? = null


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
        btnCrearActividad = v.findViewById(R.id.buttonActividades)
        btnBack = v.findViewById(R.id.button7)
        swipe = v.findViewById(R.id.swipeUpdateActividades)

        shimmerActividad = v.findViewById(R.id.shimmerActividades)

        recyclerActividades.layoutManager = LinearLayoutManager(requireContext())

        myPreferences = MyPreferences(requireContext())

        user = myPreferences.getUser()

        btnCrearActividad.setOnClickListener {
            val action = ActividadesListaDirections.actionActividadesListaToCrearActividad()

            findNavController().navigate(action)
        }

        if (myPreferences.isAdmin() != true) {
            btnCrearActividad.visibility = View.GONE
        }

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        configSwipe()

        return v
    }

    private fun configSwipe() {

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            viewModel.recargarActividades()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ActividadesListaViewModel::class.java)

        viewModel.state.observe(viewLifecycleOwner, Observer{ state ->
            Log.d("test",state)
            when(state){
                "Loading" -> {
                    recyclerActividades.isVisible = false
                    shimmerActividad.isVisible = true
                }
                "Success" -> {
                    recyclerActividades.isVisible = true
                    shimmerActividad.isVisible = false

                }
            }

        })

        viewModel.actividadesCargadas.observe(viewLifecycleOwner, Observer{ usuariosList ->
            if (usuariosList != null) {

                adapter = ActividadAdapter(usuariosList.toMutableList()) { actividad ->
                    if(myPreferences.isAdmin()) {
                        val action =
                            ActividadesListaDirections.actionActividadesListaToDetalleActividad(
                                actividad
                            )
                        findNavController().navigate(action)
                    }
                }

                recyclerActividades.adapter = adapter
            } else {
                // Maneja los errores aquí si no se pudieron obtener los paquetes
            }
        })

        viewModel.cargarActividades()





//        viewModel.obtenerActividades { actividadesList ->
//            if (actividadesList != null) {
//                adapter = ActividadAdapter(actividadesList.toMutableList()) { actividad ->
//                    if(myPreferences.isAdmin()) {
//                        val action =
//                            ActividadesListaDirections.actionActividadesListaToDetalleActividad(
//                                actividad
//                            )
//                        findNavController().navigate(action)
//                    }
//                }
//                recyclerActividades.adapter = adapter
//            }
//        }


    }
}