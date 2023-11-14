package com.example.gimnasio_grupo3.fragments.turnos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.TurnoPersonaAdapter
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.sessions.MyPreferences

class MisTurnos : Fragment() {
    lateinit var v : View
    lateinit var recyclerMisTurnos: RecyclerView
    private lateinit var btnBack: Button
    private lateinit var turnoPersonaList: List<TurnoPersona>
    private lateinit var turnoPersonaAdapter: TurnoPersonaAdapter
    private lateinit var myPreferences: MyPreferences
    private var user: Usuario? = null

    companion object {
        fun newInstance() = MisTurnos()
    }

    private lateinit var viewModel: MisTurnosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_mis_turnos, container, false)
        recyclerMisTurnos = v.findViewById(R.id.reciclerMisTurnos)
        btnBack = v.findViewById(R.id.buttonVolver)
        myPreferences = MyPreferences(requireContext())
        user = myPreferences.getUser()
        recyclerMisTurnos.layoutManager = LinearLayoutManager(requireContext())

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }


        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MisTurnosViewModel::class.java)

        viewModel.obtenerTurnoPersonas { turnosPersonas ->
            if (turnosPersonas != null) {
                turnoPersonaList = turnosPersonas
                cargarDatosEnAdapter()
            } else {
                // Manejar el caso en el que no se pudieron obtener las personas de turno
            }
        }
    }

    private fun cargarDatosEnAdapter() {

        val profesores = MisTurnosArgs.fromBundle(requireArguments()).profesoresList
        val actividades = MisTurnosArgs.fromBundle(requireArguments()).actividadesList
        val turnos = MisTurnosArgs.fromBundle(requireArguments()).turnosList

        turnoPersonaAdapter =
            user?.let { TurnoPersonaAdapter(it.id, turnoPersonaList, turnos.toList(), actividades.toList(), profesores.toList()) }!!
        recyclerMisTurnos.adapter = turnoPersonaAdapter
    }
    }

