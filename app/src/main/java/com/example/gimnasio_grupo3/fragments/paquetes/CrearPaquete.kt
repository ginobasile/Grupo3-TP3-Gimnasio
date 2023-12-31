package com.example.gimnasio_grupo3.fragments.paquetes

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Paquete
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var viewModelLista: PaquetesListaViewModel

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
            viewModelLista.recargarPaquetes()
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

            val precio = precioText.toInt()
            if (precio < 0 || precio > 100000) {
                inputPrecio.error = "El precio no debe ser mayor a 100.000"
                return@setOnClickListener
            }

            if (ticketsText.isEmpty()) {
                inputTickets.error = "La cantidad de tickets es obligatoria"
                return@setOnClickListener
            }

            val tickets = ticketsText.toInt()
            if (tickets < 0 || tickets > 1000) {
                inputTickets.error = "La cantidad de tickets no debe ser mayor a 1000"
                return@setOnClickListener
            }

            val nuevoPaquete = Paquete(nombre, tickets, precio)

            confirmAction("Crear") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.crearPaquete(nuevoPaquete) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Paquete creado exitosamente") {
                            viewModelLista.recargarPaquetes()
                            v.findNavController().navigateUp()
                        }
                    }
                } else {
                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun confirmAction(action: String, callback: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(action)
        builder.setMessage("¿Estás seguro de que deseas $action este paquete?")

        builder.setPositiveButton(action) { dialog, _ ->
            callback(true) // Llama al callback con 'true' cuando el usuario confirma
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            callback(false) // Llama al callback con 'false' cuando el usuario cancela
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearPaqueteViewModel::class.java)

        viewModelLista = ViewModelProvider(requireActivity()).get(PaquetesListaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}