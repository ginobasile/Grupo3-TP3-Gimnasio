package com.example.gimnasio_grupo3.fragments.profesores

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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.ProfesorAdapter

class ProfesoresLista : Fragment() {

    lateinit var v: View
    lateinit var recyclerProfesores: RecyclerView
    lateinit var adapter: ProfesorAdapter
    private lateinit var btnCrearProfesor: Button
    private lateinit var btnBack: Button
    lateinit var swipe: SwipeRefreshLayout
    lateinit var shimmerProfesores: LinearLayoutCompat

    companion object {
        fun newInstance() = ProfesoresLista()
    }

    private fun configSwipe() {
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            viewModel.recargarProfesores()
        }
    }

    private lateinit var viewModel: ProfesoresListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profesores_lista, container, false)
        recyclerProfesores = v.findViewById(R.id.reciclerProfesores)
        btnCrearProfesor = v.findViewById(R.id.buttonProfesores)
        btnBack = v.findViewById(R.id.button5)

        swipe = v.findViewById(R.id.swipeUpdateProfesores)
        shimmerProfesores = v.findViewById(R.id.shimmerProfesores)

        recyclerProfesores.layoutManager = LinearLayoutManager(requireContext())

        btnCrearProfesor.setOnClickListener {
            val action = ProfesoresListaDirections.actionProfesoresListaToCrearProfesor()

            findNavController().navigate(action)
        }

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        configSwipe()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfesoresListaViewModel::class.java)

        viewModel.state.observe(viewLifecycleOwner, Observer{ state ->
            Log.d("Profesor",state)
            when(state){
                "Loading" -> {
                    recyclerProfesores.isVisible = false
                    shimmerProfesores.isVisible = true
                }
                "Success" -> {
                    recyclerProfesores.isVisible = true
                    shimmerProfesores.isVisible = false
                }
            }
        })

        viewModel.profesoresCargados.observe(viewLifecycleOwner, Observer{ profesoresList ->
            if (profesoresList != null) {
                adapter = ProfesorAdapter(profesoresList.toMutableList()) { profesor ->
                        val action = ProfesoresListaDirections.actionProfesoresListaToDetalleProfesor(profesor)
                        findNavController().navigate(action)
                }

                Log.d("Profesores", profesoresList.toString())
                recyclerProfesores.adapter = adapter
            } else {
                // Maneja los errores aquí si no se pudieron obtener los paquetes
            }
        })

        viewModel.obtenerProfesores()
    }
}