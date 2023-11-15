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
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Paquete
import com.google.android.material.snackbar.Snackbar

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

    private lateinit var viewModelLista: PaquetesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_paquete, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(DetallePaqueteViewModel::class.java)

        viewModelLista = ViewModelProvider(requireActivity()).get(PaquetesListaViewModel::class.java)

        txtId = v.findViewById(R.id.textView)
        inputNombre = v.findViewById(R.id.editTextText)
        inputPrecio = v.findViewById(R.id.editTextNumber)
        inputTickets = v.findViewById(R.id.editTextNumber2)
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

        btnBack.setOnClickListener {
            viewModelLista.recargarPaquetes()
            v.findNavController().navigateUp()
        }

        btnMod.setOnClickListener {
            val nuevoNombre = inputNombre.text.toString()
            val nuevoPrecio = inputPrecio.text.toString()
            val nuevoTickets = inputTickets.text.toString()

            if (nuevoNombre.isEmpty()) {
                inputNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (nuevoPrecio.isEmpty()) {
                inputPrecio.error = "El precio es obligatorio"
                return@setOnClickListener
            }

            if (nuevoTickets.isEmpty()) {
                inputTickets.error = "La cantidad de tickets es obligatoria"
                return@setOnClickListener
            }

            if (nuevoTickets.toInt() < 0 || nuevoTickets.toInt() > 1000) {
                inputTickets.error = "La cantidad de tickets no debe ser mayor a 1000"
                return@setOnClickListener
            }

            if (nuevoPrecio.toInt() < 0 || nuevoPrecio.toInt() > 100000) {
                inputPrecio.error = "El precio no debe ser mayor a 100.000"
                return@setOnClickListener
            }

            val paqueteActualizado = Paquete(paquete.id, nuevoNombre, nuevoTickets.toInt(), nuevoPrecio.toInt())

            confirmAction("Modificar") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.actualizarPaquete(paqueteActualizado) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Paquete actualizado exitosamente") {
                            viewModelLista.recargarPaquetes()
                            v.findNavController().navigateUp()
                        }
                    }
                } else {
                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
                }
            }
        }


        btnDelete.setOnClickListener {
            confirmAction("Eliminar") { confirmed ->
                if (confirmed) {
                    viewModel.eliminarPaquete(paquete) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Paquete eliminado exitosamente") {
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
}
