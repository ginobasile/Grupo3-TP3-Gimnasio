package com.example.gimnasio_grupo3.fragments.turnos

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
import com.example.gimnasio_grupo3.adapters.TurnoAdapter
import com.example.gimnasio_grupo3.entities.Turno
import com.google.android.material.snackbar.Snackbar

class TurnosLista : Fragment() {
    lateinit var v : View
    lateinit var recyclerTurnos: RecyclerView
    lateinit var adapter: TurnoAdapter
    lateinit var turnosList: MutableList<Turno>
    private lateinit var btnCrearTurno: Button

    companion object {
        fun newInstance() = TurnosLista()
    }

    private lateinit var viewModel: TurnosListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_turnos_lista, container, false)
        recyclerTurnos = v.findViewById(R.id.reciclerTurnos)
        recyclerTurnos.layoutManager = LinearLayoutManager(requireContext())
        btnCrearTurno = v.findViewById(R.id.buttonTurnos)

        btnCrearTurno.setOnClickListener {
            val action = TurnosListaDirections.actionTurnosListaToCrearTurno()
            findNavController().navigate(action)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnosListaViewModel::class.java)

        viewModel.obtenerTurnos { turnosList ->
            if (turnosList != null) {
                adapter = TurnoAdapter(turnosList.toMutableList()) { turno ->
                    val action =
                        TurnosListaDirections.actionTurnosListaToDetalleTurno(

                        )
                    findNavController().navigate(action)
                }
                recyclerTurnos.adapter = adapter
            }
        }
    }

}