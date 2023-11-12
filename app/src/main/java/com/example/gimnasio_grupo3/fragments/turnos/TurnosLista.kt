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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.adapters.TurnoAdapter
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.example.gimnasio_grupo3.sessions.MyPreferences
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    var profesoresList = mutableListOf<Profesor>()

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
        btnBack = v.findViewById(R.id.button8)
        btnMisTurnos = v.findViewById(R.id.misTurnosButton)
        txtCantTickets = v.findViewById(R.id.textView6)
        myPreferences = MyPreferences(requireContext())
        user = myPreferences.getUser()

        if (!myPreferences.isAdmin()) {
            btnCrearTurno.visibility = View.GONE
        } else {
            btnMisTurnos.visibility = View.GONE
        }

        btnCrearTurno.setOnClickListener {
            val action = TurnosListaDirections.actionTurnosListaToCrearTurno()
            findNavController().navigate(action)
        }
        btnMisTurnos.setOnClickListener{
            val action = TurnosListaDirections.actionTurnosListaToMisTurnos()
            findNavController().navigate(action)
        }


        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TurnosListaViewModel::class.java)
        txtCantTickets.text = "Tickets: ${user?.ticketsRestantes.toString()}"

        this.obtenerProfesores { profesores ->
            if (profesores != null) {
                profesoresList = profesores.toMutableList()
            }
            Log.d("Probando", profesoresList.toString())
        }

        viewModel.obtenerTurnos { turnosList ->
            if (turnosList != null) {
                adapter = TurnoAdapter(requireContext(), turnosList.toMutableList(), profesoresList) { turno ->
                    if(myPreferences.isAdmin()) {
                        val action =
                            TurnosListaDirections.actionTurnosListaToDetalleTurno(
                                turno
                            )
                        findNavController().navigate(action)
                    }else{
                            viewModel.obtenerTurnoPersonasParaFecha(turno.id) { cantidadInscriptos ->
                                if(cantidadInscriptos != null) {
                                    if (cantidadInscriptos >= turno.cantPersonasLim){
                                        Snackbar.make(requireView(), "No hay lugar en el turno seleccionado", Snackbar.LENGTH_SHORT).show()
                                    }else{
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
        }
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
                    }
                    operacionExitosa = true
                    Snackbar.make(requireView(), "turno reservado con éxito", Snackbar.LENGTH_SHORT).show()
                }

        }else{
                Snackbar.make(requireView(), "No posees los tickets suficientes", Snackbar.LENGTH_SHORT).show()
            }

    return operacionExitosa

    }

    private fun obtenerProfesores(callback: (List<Profesor>?) -> Unit) {
        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getProfesores()

        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(
                call: Call<List<Profesor>>,
                response: Response<List<Profesor>>
            ) {
                if (response.isSuccessful) {
                    val profesoresLista = response.body()
                    Log.d("hola", profesoresLista.toString())
                    if (profesoresLista != null) {
                        profesoresList = profesoresLista.toMutableList()
                    }
                    callback(profesoresList)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                callback(null)
            }
        })
    }



}