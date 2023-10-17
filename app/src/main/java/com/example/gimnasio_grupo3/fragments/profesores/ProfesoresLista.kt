package com.example.gimnasio_grupo3.fragments.profesores

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
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.adapters.ProfesorAdapter
import com.example.gimnasio_grupo3.adapters.PaqueteAdapter
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.fragments.profesores.ProfesoresListaDirections

import com.example.gimnasio_grupo3.fragments.profesores.ProfesoresListaViewModel
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfesoresLista : Fragment() {

    lateinit var v: View
    lateinit var recyclerProfesores: RecyclerView
    lateinit var adapter: ProfesorAdapter
    lateinit var profesoresList: MutableList<Profesor>
    private lateinit var btnCrearProfesor: Button

    companion object {
        fun newInstance() = ProfesoresLista()
    }

    private lateinit var viewModel: ProfesoresListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profesores_lista, container, false)
        recyclerProfesores = v.findViewById(R.id.reciclerProfesores)
        recyclerProfesores.layoutManager = LinearLayoutManager(requireContext())
        btnCrearProfesor = v.findViewById(R.id.buttonProfesores)

        btnCrearProfesor.setOnClickListener {
            val action = ProfesoresListaDirections.actionProfesoresListaToCrearProfesor()

            findNavController().navigate(action)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfesoresListaViewModel::class.java)

        viewModel.obtenerProfesores { profesoresList ->
            if (profesoresList != null) {
                adapter = ProfesorAdapter(profesoresList.toMutableList()) { profesor ->
                    val action =
                        ProfesoresListaDirections.actionProfesoresListaToDetalleProfesor(
                            profesor
                        )
                    findNavController().navigate(action)
                }
                recyclerProfesores.adapter = adapter
            } else {

            }
        }
    }
}