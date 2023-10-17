package com.example.gimnasio_grupo3.fragments.profesores

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
import com.example.gimnasio_grupo3.entities.Profesor
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.google.android.material.snackbar.Snackbar

class DetalleProfesor : Fragment() {
    lateinit var v: View
    private lateinit var txtId: TextView
    private lateinit var inputNombre: EditText
    private lateinit var inputApellido: EditText
    private lateinit var btnMod: Button
    private lateinit var btnBack: Button
    private lateinit var btnDelete: Button

    companion object {
        fun newInstance() = DetalleProfesor()
    }

    private lateinit var viewModel: DetalleProfesorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_profesor, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DetalleProfesorViewModel::class.java)

        txtId = v.findViewById(R.id.txtIdProfesor)
        inputNombre = v.findViewById(R.id.inputModifNombreProfesor)
        inputApellido = v.findViewById(R.id.inputModifApellidoProfesor)
        btnMod = v.findViewById(R.id.btnEditarProfesor)
        btnBack = v.findViewById(R.id.btnVolverProfesor)
        btnDelete = v.findViewById(R.id.btnEliminarProfesor)

        return v
    }

    override fun onStart() {
        super.onStart()

        val profesorActual = DetalleProfesorArgs.fromBundle(requireArguments()).datosProfesor

        txtId.text = "Id: ${profesorActual.id}"
        inputNombre.setText(profesorActual.nombre)
        inputApellido.setText(profesorActual.apellido)

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnMod.setOnClickListener {
            val nuevoNombre = inputNombre.text.toString()
            val nuevoApellido = inputApellido.text.toString()

            if (nuevoNombre.isEmpty()) {
                inputNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (nuevoApellido.isEmpty()) {
                inputApellido.error = "El apellido es obligatorio"
                return@setOnClickListener
            }


            val ProfesorActualizada = Profesor(profesorActual.id, nuevoNombre, nuevoApellido)

            confirmAction("Modificar") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.actualizarProfesor(ProfesorActualizada) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Profesor actualizada exitosamente") {
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
                    viewModel.eliminarProfesor(profesorActual) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Profesor eliminada exitosamente") {
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
        builder.setMessage("¿Estás seguro de que deseas $action esta Profesor?")

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