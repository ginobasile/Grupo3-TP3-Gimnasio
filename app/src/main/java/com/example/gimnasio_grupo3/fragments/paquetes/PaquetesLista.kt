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
import com.example.gimnasio_grupo3.RetroFitProviders.PaquetesProvider
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import com.example.gimnasio_grupo3.adapters.PaqueteAdapter
import com.example.gimnasio_grupo3.entities.ActividadesRepository
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.fragments.actividades.ActividadesListaDirections
import com.example.gimnasio_grupo3.interfaces.PaqueteAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaquetesLista : Fragment() {
    lateinit var v: View
    lateinit var reciclerPaquetes: RecyclerView
    lateinit var adapter: PaqueteAdapter
    lateinit var paquetesList: MutableList<Paquete>
    private lateinit var btnCrearPaquete : Button

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

        btnCrearPaquete = v.findViewById(R.id.button1)

        btnCrearPaquete.setOnClickListener{
            val action = PaquetesListaDirections.actionPaquetesListaToCrearPaquete()

            findNavController().navigate(action)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaquetesListaViewModel::class.java)
        // TODO: Use the ViewModel

        val retrofit = PaquetesProvider().provideRetrofit()
        val apiService = retrofit.create(PaqueteAPI::class.java)
        val call = apiService.getPaquetes()

        call.enqueue(object : Callback<List<Paquete>> {
            override fun onResponse(call: Call<List<Paquete>>, response: Response<List<Paquete>>) {
                if (response.isSuccessful) {
                    paquetesList = response.body() as MutableList<Paquete>

                    // Después de obtener los datos, inicializa el adaptador
                    adapter = PaqueteAdapter(paquetesList)
                    reciclerPaquetes.adapter = adapter
                } else {
                    // La llamada no fue exitosa, maneja los errores aquí
                }
            }

            override fun onFailure(call: Call<List<Paquete>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }
}

