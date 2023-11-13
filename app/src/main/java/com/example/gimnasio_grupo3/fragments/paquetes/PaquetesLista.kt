package com.example.gimnasio_grupo3.fragments.paquetes

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.PaqueteAdapter
import com.example.gimnasio_grupo3.adapters.UsuarioAdapter
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.entities.Usuario
import com.example.gimnasio_grupo3.fragments.usuarios.UsuariosListaDirections
import com.example.gimnasio_grupo3.sessions.MyPreferences
import com.google.android.material.snackbar.Snackbar

class PaquetesLista : Fragment() {

    lateinit var v: View
    lateinit var reciclerPaquetes: RecyclerView
    lateinit var adapter: PaqueteAdapter
    lateinit var swipe: SwipeRefreshLayout
    lateinit var shimmerPaquetes: LinearLayoutCompat

    private lateinit var txtCantTickets : TextView
    private lateinit var btnCrearPaquete: Button
    private lateinit var btnBack: Button
    private lateinit var myPreferences: MyPreferences

    private var user: Usuario? = null

    companion object {
        fun newInstance() = PaquetesLista()
    }

    private lateinit var viewModel: PaquetesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_paquetes_lista, container, false)

        myPreferences = MyPreferences(requireContext())
        user = myPreferences.getUser()

        reciclerPaquetes = v.findViewById(R.id.reciclerPaquetes)

        btnBack = v.findViewById(R.id.button6)
        btnCrearPaquete = v.findViewById(R.id.button1)
        txtCantTickets = v.findViewById(R.id.ticketsListaPaquetes)
        swipe = v.findViewById(R.id.swipeUpdatePaquetes)
        shimmerPaquetes = v.findViewById(R.id.shimmerPaquetes)

        reciclerPaquetes.layoutManager = LinearLayoutManager(requireContext())


        if (user?.administrador != true) {
            btnCrearPaquete.visibility = View.GONE
        }

        btnCrearPaquete.setOnClickListener {
            val action = PaquetesListaDirections.actionPaquetesListaToCrearPaquete()

            findNavController().navigate(action)
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        configSwipe()

        return v
    }

    private fun configSwipe() {

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            viewModel.recargarPaquetes()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PaquetesListaViewModel::class.java)

        txtCantTickets.text = "Tickets: ${user?.ticketsRestantes.toString()}"

        viewModel.state.observe(viewLifecycleOwner,Observer{ state ->
            Log.d("test",state)
            when(state){
                "Loading" -> {
                    reciclerPaquetes.isVisible = false
                    shimmerPaquetes.isVisible = true
                }
                "Success" -> {
                    reciclerPaquetes.isVisible = true
                    shimmerPaquetes.isVisible = false

                }
            }

        })

        viewModel.paquetesCargados.observe(viewLifecycleOwner, Observer{ paquetesList ->
            if (paquetesList != null) {
                adapter = PaqueteAdapter(paquetesList.toMutableList()) { paquete ->
                    if (user?.administrador == true) {
                        // Si el usuario es admin, navega al detalle del paquete
                        val action = PaquetesListaDirections.actionPaquetesListaToDetallePaquete(paquete)
                        findNavController().navigate(action)
                    } else {
                        // Si el usuario no es admin, muestra un diálogo de confirmación
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                        dialogBuilder.setMessage("¿Desea comprar ${paquete.nombre}? Se le acreditarán ${paquete.cantTickets} tickets.")
                            .setPositiveButton("Sí") { _, _ ->
                                sumarTicketsAlUsuario(paquete)

                            }
                            .setNegativeButton("No") { _, _ -> }
                        val alertDialog = dialogBuilder.create()
                        alertDialog.show()
                    }
                }
                reciclerPaquetes.adapter = adapter
            } else {
                // Maneja los errores aquí si no se pudieron obtener los paquetes
            }
        })

        viewModel.cargarPaquetes()


    }

    private fun sumarTicketsAlUsuario(paquete: Paquete) {
        val misTickets = user?.ticketsRestantes.toString()
        val actualizarTickets = misTickets.toInt() + paquete.cantTickets

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
            txtCantTickets.text = "Tickets: ${actualizarTickets}"

            Snackbar.make(requireView(), "${paquete.cantTickets} sumados con éxito", Snackbar.LENGTH_SHORT).show()
        }
    }
}

