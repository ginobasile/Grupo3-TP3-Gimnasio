package com.example.gimnasio_grupo3.fragments.usuarios

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.UsuarioAdapter

class UsuariosLista : Fragment() {

    lateinit var v : View
    lateinit var recyclerUsuarios: RecyclerView
    lateinit var adapter: UsuarioAdapter
    lateinit var swipe: SwipeRefreshLayout
    lateinit var shimmerUsuarios: LinearLayoutCompat

    companion object {
        fun newInstance() = UsuariosLista()
    }

    private lateinit var viewModel: UsuariosListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_usuarios_lista, container, false)

        recyclerUsuarios = v.findViewById(R.id.reciclerUsuarios)
        recyclerUsuarios.layoutManager = LinearLayoutManager(requireContext())
        swipe = v.findViewById(R.id.swipeUpdateUsuarios)
        shimmerUsuarios = v.findViewById(R.id.shimmerUsuarios)

        configSwipe()

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

        viewModel.state.observe(viewLifecycleOwner,Observer{ state ->
            Log.d("test",state)
            when(state){
                "Loading" -> {
                    recyclerUsuarios.isVisible = false
                    shimmerUsuarios.isVisible = true
                }
                "Success" -> {
                    recyclerUsuarios.isVisible = true
                    shimmerUsuarios.isVisible = false

                }
            }

        })

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