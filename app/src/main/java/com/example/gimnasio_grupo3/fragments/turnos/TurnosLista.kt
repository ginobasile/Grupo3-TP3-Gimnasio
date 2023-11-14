package com.example.gimnasio_grupo3.fragments.turnos

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.adapters.TurnoAdapter
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.example.gimnasio_grupo3.sessions.MyPreferences
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ProtocolException

class TurnosLista : Fragment() {
    lateinit var v : View
    lateinit var recyclerTurnos: RecyclerView
    lateinit var adapter: TurnoAdapter
    private lateinit var btnCrearTurno: Button
    private lateinit var btnBack: Button
    private lateinit var btnMisTurnos: Button
    private lateinit var txtCantTickets : TextView
    private lateinit var myPreferences: MyPreferences
    private var user: Usuario? = null

    private lateinit var profesoresArray: Array<Profesor>
    private lateinit var actividadesArray: Array<Actividad>
    private lateinit var turnosArray: Array<Turno>

    lateinit var swipe: SwipeRefreshLayout
    lateinit var shimmerTurnos: LinearLayoutCompat

    companion object {
        fun newInstance() = TurnosLista()
    }

    private fun configSwipe() {
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            viewModel.obtenerTurnos()
        }
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
        btnBack = v.findViewById(R.id.button8)
        btnMisTurnos = v.findViewById(R.id.misTurnosButton)
        txtCantTickets = v.findViewById(R.id.textView6)
        myPreferences = MyPreferences(requireContext())
        user = myPreferences.getUser()

        swipe = v.findViewById(R.id.swipeUpdateTurnos)
        shimmerTurnos = v.findViewById(R.id.shimmerTurnos)

        if (!myPreferences.isAdmin()) {
            btnCrearTurno.visibility = View.INVISIBLE
        } else {
            btnMisTurnos.visibility = View.INVISIBLE
        }

        btnCrearTurno.setOnClickListener {
            val action = TurnosListaDirections.actionTurnosListaToCrearTurno(profesoresArray, actividadesArray)
            findNavController().navigate(action)
        }
        btnMisTurnos.setOnClickListener{
            irAMisTurnos()
        }

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        configSwipe()

        return v
    }

    private fun irAMisTurnos(){
        val action = TurnosListaDirections.actionTurnosListaToMisTurnos(profesoresArray, actividadesArray, turnosArray)
        findNavController().navigate(action)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnosListaViewModel::class.java)

        if (user?.administrador == true){
            txtCantTickets.visibility = View.GONE
        } else {
            txtCantTickets.text = "Tickets: ${user?.ticketsRestantes.toString()}"
        }

        viewModel.state.observe(viewLifecycleOwner, Observer{ state ->
            when(state){
                "Loading" -> {
                    recyclerTurnos.isVisible = false
                    shimmerTurnos.isVisible = true
                }
                "Success" -> {
                    recyclerTurnos.isVisible = true
                    shimmerTurnos.isVisible = false
                }
            }
        })

        viewModel.turnosCargados.observe(viewLifecycleOwner, Observer { turnosList ->
            if(turnosList != null) {

                turnosArray = turnosList.toTypedArray()

                val profesoresList: List<Profesor> = viewModel.profesoresCargados.value?.toList() ?: emptyList()
                val actividadesList: List<Actividad> = viewModel.actividadesCargadas.value?.toList() ?: emptyList()

                profesoresArray = viewModel.profesoresCargados.value?.toTypedArray() ?: emptyArray()
                actividadesArray = viewModel.actividadesCargadas.value?.toTypedArray() ?: emptyArray()


                adapter = TurnoAdapter(requireContext(), turnosList.toMutableList(), profesoresList, actividadesList) { turno ->
                    if(myPreferences.isAdmin()) {
                        val action =
                            TurnosListaDirections.actionTurnosListaToDetalleTurno(
                                turno, actividadesArray, profesoresArray
                            )
                        findNavController().navigate(action)
                    }else{
                        viewModel.obtenerTurnoPersonasParaFecha(turno.id) { cantidadInscriptos ->
                            if(cantidadInscriptos != null) {
                                if (cantidadInscriptos >= turno.cantPersonasLim){
                                    Snackbar.make(requireView(), "No hay lugar en el turno seleccionado", Snackbar.LENGTH_SHORT).show()
                                } else if (myPreferences.poseeElTurno(turno)){
                                    Snackbar.make(requireView(), "Ya estabas inscripto en el turno seleccionado", Snackbar.LENGTH_SHORT).show()
                                } else{
                                    val dialogBuilder = AlertDialog.Builder(requireContext())
                                    dialogBuilder.setMessage("¿Desea reservar el turno en la fecha: ${turno.fecha}? Se le descontaran 1 tickets.")
                                        .setPositiveButton("Sí") { _, _ ->
                                            if(restarTicketsAlUsuario()) {
                                                val nuevoTurnoPersona =
                                                    user?.id?.let { TurnoPersona(it, turno.id) }
                                                if (nuevoTurnoPersona != null) {
                                                    viewModel.crearTurnoPersona(
                                                        nuevoTurnoPersona
                                                    ) { estado ->
                                                        Snackbar.make(
                                                            v,
                                                            estado,
                                                            Snackbar.LENGTH_LONG
                                                        ).show()
                                                        irAMisTurnos()
                                                    }
                                                }
                                            }
                                        }
                                        .setNegativeButton("No") { _, _ -> }
                                    val alertDialog = dialogBuilder.create()
                                    alertDialog.show()
                                }
                            }
                        }
                    }
                }
                recyclerTurnos.adapter = adapter
            }
        })

        viewModel.obtenerTurnos()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun restarTicketsAlUsuario() : Boolean {
        val misTickets = user?.ticketsRestantes
        var operacionExitosa = false
            if(misTickets!=null && misTickets > 0) {
                val actualizarTickets = misTickets - 1

                user?.let { usuario ->
                    val usuarioActualizado = Usuario(
                        usuario.id,
                        usuario.nombre,
                        usuario.apellido,
                        usuario.mail,
                        usuario.password,
                        usuario.altura,
                        usuario.peso,
                        usuario.edad,
                        usuario.contacto,
                        usuario.administrador,
                        usuario.dni,
                        actualizarTickets
                    )
                    viewModel.actualizarTickets(usuarioActualizado){ estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()
                        myPreferences.setUser(usuarioActualizado)
                        txtCantTickets.text = usuarioActualizado.ticketsRestantes.toString()
                    }
                    operacionExitosa = true
                    Snackbar.make(requireView(), "turno reservado con éxito", Snackbar.LENGTH_SHORT).show()
                }

        }else{
                Snackbar.make(requireView(), "No posees los tickets suficientes", Snackbar.LENGTH_SHORT).show()
            }

    return operacionExitosa

    }
}