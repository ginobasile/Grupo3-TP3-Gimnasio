package com.example.gimnasio_grupo3.fragments.turnos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar


class CrearTurno : Fragment() {
    lateinit var v : View
    private lateinit var inputCantPersonas: EditText
    private lateinit var inputFecha: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    lateinit var actividadesList: MutableList<Actividad>
    lateinit var profesoresList: MutableList<Profesor>
    lateinit var actividadesSpinner: Spinner
    lateinit var profesoresSpinner: Spinner
    lateinit var idActividad: String
    lateinit var idProfesor: String
    companion object {
        fun newInstance() = CrearTurno()
    }

    private lateinit var viewModel: CrearTurnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_turno, container, false)

        inputCantPersonas = v.findViewById(R.id.inputCantPersonasCrear)
        btnCreate = v.findViewById(R.id.actividadCrear)
        btnBack = v.findViewById(R.id.volver)
        actividadesSpinner = v.findViewById(R.id.planets_spinner)
        profesoresSpinner = v.findViewById(R.id.planets_spinner2)
        inputFecha = v.findViewById(R.id.inputFecha)
        inputFecha.setOnClickListener {
            mostrarCalendario(v)
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnCreate.setOnClickListener {
            val actividad = idActividad
            val profesor =  idProfesor
            val cantPersonas = inputCantPersonas.text.toString()
            val fecha = inputFecha.text.toString()

            if (cantPersonas.isEmpty()) {
                inputCantPersonas.error = "Debe indicar cantidad de personas"
                return@setOnClickListener
            }
            if (fecha.isEmpty()) {
                inputFecha.error = "Debe indicar fecha"
                return@setOnClickListener
            }

            val nuevoTurno = Turno(profesor, actividad, fecha, cantPersonas.toInt())

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

        viewModel.obtenerProfesores { listaProfesores ->
            if (listaProfesores != null) {
                profesoresList = listaProfesores as MutableList<Profesor>

                val adapter = ArrayAdapter(
                    v.context,
                    android.R.layout.simple_spinner_item,
                    formatoProfesor(profesoresList)
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                profesoresSpinner.adapter = adapter
            }
        }

        profesoresSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idProfesor = profesoresList[position].id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        viewModel.obtenerActividades { listaActividades ->
            if (listaActividades != null) {
                actividadesList = listaActividades as MutableList<Actividad>

                val adapter = ArrayAdapter(
                    v.context,
                    android.R.layout.simple_spinner_item,
                    formatoActividad(actividadesList)
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                actividadesSpinner.adapter = adapter
            }
        }

        actividadesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                idActividad = actividadesList[position].id.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun formatoActividad(actividadesList: MutableList<Actividad>): List<String> {
        return actividadesList.map { "${it.name}, ${it.duration} min" }
    }

    fun formatoProfesor(profesoresList: MutableList<Profesor>): List<String> {
        return profesoresList.map { "${it.nombre}, ${it.apellido}" }
    }

    fun mostrarCalendario(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            {_ , selectedYear, selectedMonth, selectedDay ->
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