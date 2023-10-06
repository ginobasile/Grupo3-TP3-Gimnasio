package com.example.gimnasio_grupo3.fragments.actividades

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
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import com.example.gimnasio_grupo3.adapters.PaqueteAdapter
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.fragments.paquetes.PaquetesListaDirections
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActividadesLista : Fragment() {

    lateinit var v : View
    lateinit var recyclerActividades : RecyclerView
    lateinit var adapter : ActividadAdapter
    lateinit var actividadesList: MutableList<Actividad>

    companion object {
        fun newInstance() = ActividadesLista()
    }

    private lateinit var viewModel: ActividadesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_actividades_lista, container, false)
        recyclerActividades = v.findViewById(R.id.reciclerActividades)
        recyclerActividades.layoutManager = LinearLayoutManager(requireContext())
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ActividadesListaViewModel::class.java)

        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getActividad()

        call.enqueue(object : Callback<List<Actividad>> {
            override fun onResponse(call: Call<List<Actividad>>, response: Response<List<Actividad>>) {
                if (response.isSuccessful) {
                    actividadesList = response.body() as MutableList<Actividad>
                    // Después de obtener los datos, inicializa el adaptador
                    adapter = ActividadAdapter(actividadesList) { actividad ->

                        val snackbar = Snackbar.make(v, actividad.toString(), Snackbar.LENGTH_LONG)

                        snackbar.show()
                    }
                    recyclerActividades.adapter = adapter
                } else {
                    // La llamada no fue exitosa, maneja los errores aquí
                }
            }

            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                // Maneja errores de conexión aquí
            }
        })
    }
}