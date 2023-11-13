package com.example.gimnasio_grupo3.fragments.actividades

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
import com.example.gimnasio_grupo3.entities.Actividad
import com.google.android.material.snackbar.Snackbar

class DetalleActividad : Fragment() {
    lateinit var v: View
    private lateinit var txtId: TextView
    private lateinit var inputNombre: EditText
    private lateinit var inputDuracion: EditText
    private lateinit var btnMod: Button
    private lateinit var btnBack: Button
    private lateinit var btnDelete: Button

    companion object {
        fun newInstance() = DetalleActividad()
    }

    private lateinit var viewModel: DetalleActividadViewModel
    private lateinit var viewModelLista: ActividadesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_actividad, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(DetalleActividadViewModel::class.java)
        viewModelLista = ViewModelProvider(requireActivity()).get(ActividadesListaViewModel::class.java)

        txtId = v.findViewById(R.id.textViewIDActividad)
        inputNombre = v.findViewById(R.id.editTextAct)
        inputDuracion = v.findViewById(R.id.editTextAct2)
        btnMod = v.findViewById(R.id.ActModi)
        btnBack = v.findViewById(R.id.ActVolver)
        btnDelete = v.findViewById(R.id.ActDelete)

        return v
    }

    override fun onStart() {
        super.onStart()

        val actividadActual = DetalleActividadArgs.fromBundle(requireArguments()).actividad

        txtId.text = "Id: ${actividadActual.id}"
        inputNombre.setText(actividadActual.name)
        inputDuracion.setText(actividadActual.duration.toString())

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnMod.setOnClickListener {
            val nuevoNombre = inputNombre.text.toString()
            val nuevaDuracion = inputDuracion.text.toString()

            if (nuevoNombre.isEmpty()) {
                inputNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (nuevaDuracion.isEmpty()) {
                inputDuracion.error = "La duracion es obligatoria"
                return@setOnClickListener
            }

            val ActividadActualizada = Actividad(actividadActual.id, nuevoNombre, nuevaDuracion.toInt())

            confirmAction("Modificar") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.actualizarActividad(ActividadActualizada) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Actividad actualizada exitosamente") {

                            viewModelLista.recargarActividades()
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
                    viewModel.eliminarActividad(actividadActual) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Actividad eliminada exitosamente") {
                            viewModelLista.recargarActividades()
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
        builder.setMessage("¿Estás seguro de que deseas $action esta Actividad?")

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