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
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Paquete
import com.example.gimnasio_grupo3.fragments.paquetes.CrearPaquete
import com.example.gimnasio_grupo3.fragments.paquetes.CrearPaqueteViewModel
import com.google.android.material.snackbar.Snackbar

class CrearActividad : Fragment() {

    lateinit var v : View
    private lateinit var inputNombre: EditText
    private lateinit var inputDuracion: EditText
    private lateinit var inputUrl: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    companion object {
        fun newInstance() = CrearActividad()
    }

    private lateinit var viewModel: CrearActividadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_actividad, container, false)

        inputNombre = v.findViewById(R.id.editNombreActividad)
        inputDuracion = v.findViewById(R.id.editDuracion)
        inputUrl = v.findViewById(R.id.editURL)
        btnCreate = v.findViewById(R.id.actividadCrear)
        btnBack = v.findViewById(R.id.volver)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnCreate.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val duracionText = inputDuracion.text.toString()
            val url = inputUrl.text.toString()

            if (nombre.isEmpty()) {
                inputNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (duracionText.isEmpty()) {
                inputDuracion.error = "El precio es obligatorio"
                return@setOnClickListener
            }

            if (url.isEmpty()) {
                inputUrl.error = "Debe ingresar un URL de imagen"
                return@setOnClickListener
            }
            val dur = duracionText.toInt()
            val nuevoActividad = Actividad(nombre, dur, url)

            confirmAction("Crear") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.crearActividad(nuevoActividad) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Actividad creada exitosamente") {
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
        builder.setMessage("¿Estás seguro de que deseas $action este actividad?")

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
        viewModel = ViewModelProvider(this).get(CrearActividadViewModel::class.java)
        // TODO: Use the ViewModel
    }
}