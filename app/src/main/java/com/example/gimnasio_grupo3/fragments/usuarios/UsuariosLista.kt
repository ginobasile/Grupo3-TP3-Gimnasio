package com.example.gimnasio_grupo3.fragments.usuarios

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.UsuarioAdapter

class UsuariosLista : Fragment() {

    private lateinit var btnVolver : Button

    lateinit var v : View
    lateinit var recyclerUsuarios: RecyclerView
    lateinit var adapter: UsuarioAdapter
    lateinit var swipe: SwipeRefreshLayout

    companion object {
        fun newInstance() = UsuariosLista()
    }

    private lateinit var viewModel: UsuariosListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_usuarios_lista, container, false)

        recyclerUsuarios = v.findViewById(R.id.reciclerUsuarios)
        recyclerUsuarios.layoutManager = LinearLayoutManager(requireContext())
        swipe = v.findViewById(R.id.swipeUpdateUsuarios)

        btnVolver = v.findViewById(R.id.button5)

        configSwipe()

        btnVolver.setOnClickListener {
            v.findNavController().navigateUp()
        }

        return v
    }

    private fun configSwipe() {

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            viewModel.recargarUsuarios()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(UsuariosListaViewModel::class.java)



        viewModel.usuariosCargados.observe(viewLifecycleOwner, Observer{ usuariosList ->
            if (usuariosList != null) {

                adapter = UsuarioAdapter(usuariosList.toMutableList()) { usuario ->
                    val action =
                        UsuariosListaDirections.actionUsuariosListaToDetalleUsuario(usuario)
                    findNavController().navigate(action)
                }

                recyclerUsuarios.adapter = adapter
            } else {
                // Maneja los errores aquí si no se pudieron obtener los paquetes
            }
        })

        viewModel.cargarUsuarios()

    }
}