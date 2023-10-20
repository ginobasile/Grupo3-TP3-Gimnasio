package com.example.gimnasio_grupo3.fragments.turnos

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.adapters.ActividadAdapter
import com.example.gimnasio_grupo3.adapters.ProfesorAdapter
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.fragments.profesores.ProfesoresListaDirections
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.util.concurrent.Executors


class CrearTurno : Fragment() {

    private lateinit var actividadesMutableList :  MutableList<Actividad>
    private lateinit var profesoresMutableList: MutableList<Profesor>
    lateinit var v : View
    private lateinit var inputActividad: EditText

    private lateinit var inputProfesor: EditText
    private lateinit var inputCantPersonas: EditText
    private lateinit var inputFecha: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    private lateinit var selecActividad: TextInputEditText
    private lateinit var selecProfesor: TextInputEditText

    companion object {
        fun newInstance() = CrearTurno()
    }

    private lateinit var viewModel: CrearTurnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_turno, container, false)




//       spinnerActividad.adapter = actividadesList
//        spinnerProfesor.adapter = profesoresList
//        selecActividad = v.findViewById(R.id.textInputLayoutActividad)
//        selecProfesor = v.findViewById(R.id.textInputLayoutProfesor)
        inputCantPersonas = v.findViewById(R.id.inputCantPersonasCrear)
        btnCreate = v.findViewById(R.id.actividadCrear)
        btnBack = v.findViewById(R.id.volver)
        inputFecha = v.findViewById(R.id.inputFecha)
        inputFecha.setOnClickListener {
            mostrarCalendario(v)
        }

//        Executors.newSingleThreadExecutor().execute {
//            try {
//                actividadesList = viewModel.obtenerActividades() as MutableList<Actividad>
//
//                profesoresList = viewModel.obtenerProfesores() as MutableList<Profesor>
//
//
////                requireActivity().runOnUiThread {
////                    spinnerActividad.adapter = actividadesList
////                    spinnerProfesor.adapter = profesoresList
////                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                requireActivity().runOnUiThread {
//                    Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        return v
    }

    fun mostrarCalendario(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                inputFecha.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }


    override fun onStart() {
        super.onStart()
//        actividadesList = viewModel.obtenerActividades() as MutableList<Actividad>
//        profesoresList = viewModel.obtenerProfesores() as MutableList<Profesor>
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
//        viewModel.obtenerProfesores { profesoresList ->
//            if (profesoresList != null) {
//                profesoresList = ProfesorAdapter(profesoresList.toMutableList()) { profesor ->
//                    val action =
//                        ProfesoresListaDirections.actionProfesoresListaToDetalleProfesor(
//                            profesor
//                        )
//                    findNavController().navigate(action)
//                }
//
//            }
//        }
    }

}