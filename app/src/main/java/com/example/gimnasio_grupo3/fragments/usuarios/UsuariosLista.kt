package com.example.gimnasio_grupo3.fragments.usuarios

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.UsuarioAdapter

class UsuariosLista : Fragment() {

    lateinit var v : View
    lateinit var recyclerUsuarios: RecyclerView
    lateinit var adapter: UsuarioAdapter

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


        return v
    }

    //override fun onActivityCreated(savedInstanceState: Bundle?) {
   //     super.onActivityCreated(savedInstanceState)
   //     viewModel = ViewModelProvider(this).get(UsuariosListaViewModel::class.java)

   //     viewModel.obtenerUsuarios { usuariosList ->
    //        if (usuariosList != null) {

   //             adapter = UsuarioAdapter(usuariosList.toMutableList()) { usuario ->
     //               val action =
     //                   UsuariosListaDirections.actionUsuariosListaToDetalleUsuario()
     //               findNavController().navigate(action)
     //           }

       //         recyclerUsuarios.adapter = adapter
       //     } else {
        //        // Maneja los errores aquí si no se pudieron obtener los paquetes
      //      }
     //   }
  //  }



}