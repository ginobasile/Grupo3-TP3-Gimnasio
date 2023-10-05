package com.example.gimnasio_grupo3.fragments.paquetes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.RetroFitProviders.PaquetesProvider
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CrearPaquete : Fragment() {
    lateinit var v : View
    private lateinit var inputNombre: EditText
    private lateinit var inputPrecio: EditText
    private lateinit var inputTickets: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    companion object {
        fun newInstance() = CrearPaquete()
    }

    private lateinit var viewModel: CrearPaqueteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_paquete, container, false)

        inputNombre = v.findViewById(R.id.editTextText)
        inputPrecio = v.findViewById(R.id.editTextNumber2)
        inputTickets = v.findViewById(R.id.editTextNumber)
        btnCreate = v.findViewById(R.id.button2)
        btnBack = v.findViewById(R.id.button3)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnCreate.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val precioText = inputPrecio.text.toString()
            val ticketsText = inputTickets.text.toString()

            if (nombre.isEmpty()) {
                inputNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (precioText.isEmpty()) {
                inputPrecio.error = "El precio es obligatorio"
                return@setOnClickListener
            }

            if (ticketsText.isEmpty()) {
                inputTickets.error = "La cantidad de tickets es obligatoria"
                return@setOnClickListener
            }

            val precio = precioText.toInt()
            val tickets = ticketsText.toInt()

            val nuevoPaquete = Paquete(nombre, precio, tickets)

            val retrofit = PaquetesProvider().provideRetrofit()
            val apiService = retrofit.create(APIMethods::class.java)

            val call = apiService.createPaquete(nuevoPaquete)

            call.enqueue(object : Callback<Paquete> {
                override fun onResponse(call: Call<Paquete>, response: Response<Paquete>) {
                    if (response.isSuccessful) {
                        // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                        Snackbar.make(v, "Paquete creado exitosamente", Snackbar.LENGTH_LONG)
                            .show()
                        v.findNavController().navigateUp()
                    } else {
                        // La actualización no fue exitosa, maneja los errores aquí
                        Snackbar.make(v, "Error al crear el paquete", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Paquete>, t: Throwable) {
                    // Maneja errores de conexión aquí
                    Snackbar.make(
                        v,
                        "Error de conexión al crear el paquete",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearPaqueteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}