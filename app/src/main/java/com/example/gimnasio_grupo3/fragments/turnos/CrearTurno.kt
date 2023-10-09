package com.example.gimnasio_grupo3.fragments.turnos

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
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.fragments.actividades.CrearActividad
import com.example.gimnasio_grupo3.fragments.actividades.CrearActividadViewModel
import com.google.android.material.snackbar.Snackbar

class CrearTurno : Fragment() {
    lateinit var v : View
    private lateinit var inputActividad: EditText
    private lateinit var inputProfesor: EditText
    private lateinit var inputCantPersonas: EditText
    private lateinit var inputFecha: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    companion object {
        fun newInstance() = CrearTurno()
    }

    private lateinit var viewModel: CrearTurnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_turno, container, false)

        inputActividad = v.findViewById(R.id.inputAct)
        inputProfesor = v.findViewById(R.id.editProfesor)
        inputCantPersonas = v.findViewById(R.id.inputCantPersonasCrear)
        inputFecha = v.findViewById(R.id.inputFechaCrear)
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
            val actividad = inputActividad.text.toString()
            val profesor = inputProfesor.text.toString()
            val cantPersonas = inputCantPersonas.text.toString()
            val fecha = inputFecha.text.toString()


            if (actividad.isEmpty()) {
                inputActividad.error = "La actividad es obligatoria"
                return@setOnClickListener
            }

            if (profesor.isEmpty()) {
                inputProfesor.error = "El profesor es obligatorio"
                return@setOnClickListener
            }

            if (cantPersonas.isEmpty()) {
                inputCantPersonas.error = "Debe indicar cantidad de personas"
                return@setOnClickListener
            }
            if (fecha.isEmpty()) {
                inputFecha.error = "Debe indicar fecha"
                return@setOnClickListener
            }

            val nuevoTurno = Turno(profesor, actividad, fecha,cantPersonas.toInt())

            confirmAction("Crear") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.crearTurno(nuevoTurno) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Turno creado exitosamente") {
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
        builder.setMessage("¿Estás seguro de que deseas $action este turno?")

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
        viewModel = ViewModelProvider(this).get(CrearTurnoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}