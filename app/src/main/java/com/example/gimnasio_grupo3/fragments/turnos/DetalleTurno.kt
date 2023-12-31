package com.example.gimnasio_grupo3.fragments.turnos

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class DetalleTurno : Fragment() {
    lateinit var v: View
    private lateinit var inputCantPersonas: EditText
    private lateinit var inputFecha: EditText
    private lateinit var btnMod: Button
    private lateinit var btnBack: Button
    private lateinit var btnDelete: Button
    private lateinit var txtId: TextView
    lateinit var actividadesSpinner: Spinner
    lateinit var profesoresSpinner: Spinner
    lateinit var idActividad: String
    lateinit var idProfesor: String

    companion object {
        fun newInstance() = DetalleTurno()
    }

    private lateinit var viewModel: DetalleTurnoViewModel
    private lateinit var viewModelLista: TurnosListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_turno, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DetalleTurnoViewModel::class.java)

        viewModelLista = ViewModelProvider(requireActivity()).get(TurnosListaViewModel::class.java)

        txtId = v.findViewById(R.id.textViewIDTurno)
        inputCantPersonas = v.findViewById(R.id.editTextCantPersonas)
        btnMod = v.findViewById(R.id.ActModi)
        btnBack = v.findViewById(R.id.ActVolver)
        btnDelete = v.findViewById(R.id.ActDelete)
        actividadesSpinner = v.findViewById(R.id.spinner)
        profesoresSpinner = v.findViewById(R.id.spinner2)
        inputFecha = v.findViewById(R.id.inputFecha)

        return v
    }

    override fun onStart() {
        super.onStart()

        val turnoActual = DetalleTurnoArgs.fromBundle(requireArguments()).datosTurno
        val profesores = DetalleTurnoArgs.fromBundle(requireArguments()).profesoresList
        val actividades = DetalleTurnoArgs.fromBundle(requireArguments()).actividadesList

        inputFecha.setOnClickListener {
            mostrarCalendario(v, turnoActual.fecha)
        }

        Log.d("DetalleTurno", profesores.joinToString(", "))
        Log.d("DetalleTurno", actividades.joinToString(", "))

        txtId.text = "Id: ${turnoActual.id}"
        inputCantPersonas.setText(turnoActual.cantPersonasLim.toString())
        inputFecha.setText(turnoActual.fecha)


        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnMod.setOnClickListener {
            val nuevaActividad = idActividad
            val nuevoProfesor = idProfesor
            val nuevaCantidad = inputCantPersonas.text.toString()
            val nuevaFecha = inputFecha.text.toString()

            if (nuevaCantidad.isEmpty()) {
                inputCantPersonas.error = "Indique una cantidad de personas"
                return@setOnClickListener
            }

            if (nuevaCantidad.toInt() < 0 || nuevaCantidad.toInt() > 100) {
                inputCantPersonas.error = "La cantidad de personas debe estar entre 1 y 100"
                return@setOnClickListener
            }

            if (nuevaFecha.isEmpty()) {
                inputFecha.error = "la fecha es obligatoria"
                return@setOnClickListener
            }

            val turnoActualizado = Turno(
                turnoActual.id,
                nuevoProfesor,
                nuevaActividad,
                nuevaFecha,
                nuevaCantidad.toInt()
            )

            confirmAction("Modificar") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.actualizarTurno(turnoActualizado) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Turno actualizado exitosamente") {
                            viewModelLista.obtenerTurnos()
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
                    viewModel.eliminarTurno(turnoActual) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Turno eliminado exitosamente") {
                            viewModelLista.obtenerTurnos()
                            v.findNavController().navigateUp()
                        }
                    }
                } else {
                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        val adapterProfesor = ArrayAdapter(
            v.context,
            android.R.layout.simple_spinner_item,
            formatoProfesor(profesores)
        )
        adapterProfesor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        profesoresSpinner.adapter = adapterProfesor

        val profesor = profesores.find { it.id == turnoActual.idProfesor.toInt() }

        if(profesor != null) {
            val index = profesores.indexOf(profesor)

//                    Log.d("ListaDeProfesores", index.toString())
//                    Log.d("ListaDeProfesores", profesor.toString())
//                    Log.d("ListaDeProfesores", profesoresList.size.toString())
//                    Log.d("ListaDeProfesores", profesoresList.toString())

            if (index != -1) {
                profesoresSpinner.setSelection(index)
            }
        }

        profesoresSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idProfesor = profesores[position].id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val adapterActividad = ArrayAdapter(
            v.context,
            android.R.layout.simple_spinner_item,
            formatoActividad(actividades)
        )
        adapterActividad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        actividadesSpinner.adapter = adapterActividad

        val actividad = actividades.find { it.id == turnoActual.idActividad.toInt() }

        if(actividad != null){
            val index = actividades.indexOf(actividad)

//                    Log.d("ListaDeActividades", index.toString())
//                    Log.d("ListaDeActividades", actividad.toString())
//                    Log.d("ListaDeActividades", actividadesList.size.toString())
//                    Log.d("ListaDeActividades", actividadesList.toString())

            if(index != -1) {
                actividadesSpinner.setSelection(actividades.indexOf(actividad))
            }
        }


        actividadesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idActividad = actividades[position].id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
    fun formatoActividad(actividadesList: Array<Actividad>): List<String> {
        return actividadesList.map { "${it.name}, ${it.duration} min" }
    }

    fun formatoProfesor(profesoresList: Array<Profesor>): List<String> {
        return profesoresList.map { "${it.nombre}, ${it.apellido}" }
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

    fun mostrarCalendario(view: View, fechaSeleccionada: String) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val fechaSeleccionadaParts = fechaSeleccionada.split("/")
        val selectedYear = fechaSeleccionadaParts[2].toInt()
        val selectedMonth = fechaSeleccionadaParts[1].toInt() - 1 // Restar 1 porque en Calendar.MONTH enero es 0
        val selectedDay = fechaSeleccionadaParts[0].toInt()

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
        datePickerDialog.datePicker.init(selectedYear, selectedMonth, selectedDay, null)
        datePickerDialog.show()
    }
}