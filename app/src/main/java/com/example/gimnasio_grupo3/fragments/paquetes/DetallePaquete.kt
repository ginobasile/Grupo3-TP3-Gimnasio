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

class DetallePaquete : Fragment() {
    lateinit var v: View
    private lateinit var txtId: TextView
    private lateinit var inputNombre: EditText
    private lateinit var inputPrecio: EditText
    private lateinit var inputTickets: EditText
    private lateinit var btnMod: Button
    private lateinit var btnBack: Button
    private lateinit var btnDelete: Button

    companion object {
        fun newInstance() = DetallePaquete()
    }

    private lateinit var viewModel: DetallePaqueteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_paquete, container, false)

        txtId = v.findViewById(R.id.textView)
        inputNombre = v.findViewById(R.id.editTextText)
        inputPrecio = v.findViewById(R.id.editTextNumber2)
        inputTickets = v.findViewById(R.id.editTextNumber)
        btnMod = v.findViewById(R.id.button2)
        btnBack = v.findViewById(R.id.button3)
        btnDelete = v.findViewById(R.id.button)

        return v
    }

    override fun onStart() {
        super.onStart()

        val paquete = DetallePaqueteArgs.fromBundle(requireArguments()).datosPaquete

        txtId.text = "Id: ${paquete.id}"
        inputNombre.setText(paquete.nombre)
        inputPrecio.setText(paquete.precio.toString())
        inputTickets.setText(paquete.cantTickets.toString())

        val retrofit = PaquetesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)

        btnMod.setOnClickListener {

            val nuevoNombre = inputNombre.text.toString()
            val nuevoPrecio = inputPrecio.text.toString().toInt()
            val nuevoTickets = inputTickets.text.toString().toInt()

            val paqueteActualizado = Paquete(paquete.id, nuevoNombre, nuevoTickets, nuevoPrecio)

            val call = apiService.updatePaquete(paquete.id.toString(), paqueteActualizado)

            call.enqueue(object : Callback<Paquete> {
                override fun onResponse(call: Call<Paquete>, response: Response<Paquete>) {
                    if (response.isSuccessful) {
                        // Actualización exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                        Snackbar.make(v, "Paquete actualizado exitosamente", Snackbar.LENGTH_LONG)
                            .show()
                        v.findNavController().navigateUp()
                    } else {
                        // La actualización no fue exitosa, maneja los errores aquí
                        Snackbar.make(v, "Error al actualizar el paquete", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Paquete>, t: Throwable) {
                    // Maneja errores de conexión aquí
                    Snackbar.make(
                        v,
                        "Error de conexión al actualizar el paquete",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            })
        }

        btnDelete.setOnClickListener {
            val call = apiService.deletePaquete(paquete.id.toString())

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Eliminación exitosa, puedes mostrar un mensaje o realizar otras acciones si es necesario
                        Snackbar.make(v, "Paquete eliminado exitosamente", Snackbar.LENGTH_LONG)
                            .show()
                        v.findNavController().navigateUp()
                    } else {
                        // La eliminación no fue exitosa, maneja los errores aquí
                        Snackbar.make(v, "Error al eliminar el paquete", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Maneja errores de conexión aquí
                    Snackbar.make(v, "Error de conexión al eliminar el paquete", Snackbar.LENGTH_LONG)
                        .show()
                }
            })
        }

        fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            viewModel = ViewModelProvider(this).get(DetallePaqueteViewModel::class.java)
            // TODO: Use the ViewModel
        }

    }
}