package com.example.gimnasio_grupo3.fragments.paquetes

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
import com.example.gimnasio_grupo3.adapters.PaqueteAdapter
import com.example.gimnasio_grupo3.entities.Paquete

class PaquetesLista : Fragment() {
    lateinit var v: View
    lateinit var reciclerPaquetes: RecyclerView
    lateinit var adapter: PaqueteAdapter
    lateinit var paquetesList: MutableList<Paquete>
    private lateinit var btnCrearPaquete: Button
    private lateinit var btnBack: Button

    companion object {
        fun newInstance() = PaquetesLista()
    }

    private lateinit var viewModel: PaquetesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_paquetes_lista, container, false)
        reciclerPaquetes = v.findViewById(R.id.reciclerPaquetes)
        reciclerPaquetes.layoutManager = LinearLayoutManager(requireContext())
        btnBack = v.findViewById(R.id.button6)

        btnCrearPaquete = v.findViewById(R.id.button1)

        btnCrearPaquete.setOnClickListener {
            val action = PaquetesListaDirections.actionPaquetesListaToCrearPaquete()

            findNavController().navigate(action)
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaquetesListaViewModel::class.java)

        viewModel.obtenerPaquetes { paquetesList ->
            if (paquetesList != null) {
                adapter = PaqueteAdapter(paquetesList.toMutableList()) { paquete ->
                    val action =
                        PaquetesListaDirections.actionPaquetesListaToDetallePaquete(paquete)
                    findNavController().navigate(action)
                }
                reciclerPaquetes.adapter = adapter
            }
        }
    }
}

