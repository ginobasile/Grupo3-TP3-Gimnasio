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
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Turno
import com.google.android.material.snackbar.Snackbar

class DetalleTurno : Fragment() {
    lateinit var v: View
    private lateinit var txtId: TextView
    private lateinit var actividadTurno: EditText
    private lateinit var fechaTurno: EditText
    private lateinit var profesorTurno: EditText
    private lateinit var cantPersonas: EditText
    private lateinit var btnMod: Button
    private lateinit var btnBack: Button
    private lateinit var btnDelete: Button

    companion object {
        fun newInstance() = DetalleTurno()
    }

    private lateinit var viewModel: DetalleTurnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_turno, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DetalleTurnoViewModel::class.java)

        txtId = v.findViewById(R.id.textViewIDTurno)
        actividadTurno = v.findViewById(R.id.editTextActTurno)
        profesorTurno = v.findViewById(R.id.editTextProfe)
        cantPersonas = v.findViewById(R.id.textViewCantPersonas)
        fechaTurno = v.findViewById(R.id.editTextfecha)
        btnMod = v.findViewById(R.id.ActModi)
        btnBack = v.findViewById(R.id.ActVolver)
        btnDelete = v.findViewById(R.id.ActDelete)

        return v
    }

//    override fun onStart() {
//        super.onStart()
//
//        val turnoActual = DetalleTurnoArgs.fromBundle(requireArguments()).turno
//
//        txtId.text = "Id: ${turnoActual.id}"
//        actividadTurno.setText(turnoActual.idActividad)
//        profesorTurno.setText(turnoActual.idProfesor)
//        cantPersonas.setText(turnoActual.cantPersonasLim)
//        fechaTurno.setText(turnoActual.fecha)
//
//
//        btnBack.setOnClickListener {
//            v.findNavController().navigateUp()
//        }
//
//        btnMod.setOnClickListener {
//            val nuevaActividad = actividadTurno.text.toString()
//            val nuevoProfesor = profesorTurno.text.toString()
//            val nuevaCantidad = cantPersonas.text.toString()
//            val nuevaFecha = fechaTurno.text.toString()
//
//            if (nuevaActividad.isEmpty()) {
//                actividadTurno.error = "Indique Actividad"
//                return@setOnClickListener
//            }
//
//            if (nuevoProfesor.isEmpty()) {
//                profesorTurno.error = "Indique profesor"
//                return@setOnClickListener
//            }
//
//            if (nuevaCantidad.isEmpty()) {
//                cantPersonas.error = "Indique una cantidad"
//                return@setOnClickListener
//            }
//            if (nuevaFecha.isEmpty()) {
//                fechaTurno.error = "la fecha es obligatoria"
//                return@setOnClickListener
//            }
//
//            val turnoActualizado = Turno(turnoActual.id, nuevoProfesor, nuevaActividad, nuevaFecha,nuevaCantidad.toInt() )
//
//            confirmAction("Modificar") { confirmed ->
//                if (confirmed) {
//                    // Llama a la función en el ViewModel y pasa un callback
//                    viewModel.actualizarTurno(turnoActualizado) { estado ->
//                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()
//
//                        if (estado == "Turno actualizado exitosamente") {
//                            v.findNavController().navigateUp()
//                        }
//                    }
//                } else {
//                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
//                }
//            }
//        }
//
//
//        btnDelete.setOnClickListener {
//            confirmAction("Eliminar") { confirmed ->
//                if (confirmed) {
//                    viewModel.eliminarTurno(turnoActual) { estado ->
//                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()
//
//                        if (estado == "Turno eliminado exitosamente") {
//                            v.findNavController().navigateUp()
//                        }
//                    }
//                } else {
//                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
//                }
//            }
//        }
//
//    }
//
//    private fun confirmAction(action: String, callback: (Boolean) -> Unit) {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle(action)
//        builder.setMessage("¿Estás seguro de que deseas $action este Turno?")
//
//        builder.setPositiveButton(action) { dialog, _ ->
//            callback(true) // Llama al callback con 'true' cuando el usuario confirma
//            dialog.dismiss()
//        }
//
//        builder.setNegativeButton("Cancelar") { dialog, _ ->
//            callback(false) // Llama al callback con 'false' cuando el usuario cancela
//            dialog.dismiss()
//        }
//
//        val dialog = builder.create()
//        dialog.show()
//    }

}