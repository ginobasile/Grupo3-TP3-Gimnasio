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
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.fragments.profesores.CrearProfesorViewModel
import com.example.gimnasio_grupo3.fragments.profesores.ProfesoresListaViewModel
import com.google.android.material.snackbar.Snackbar

class CrearProfesor : Fragment() {

    lateinit var v : View
    private lateinit var inputNombre: EditText
    private lateinit var inputApellido: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    companion object {
        fun newInstance() = CrearProfesor()
    }

    private lateinit var viewModel: CrearProfesorViewModel
    private lateinit var viewModelLista: ProfesoresListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_profesor, container, false)

        inputNombre = v.findViewById(R.id.inputCrearNombreProfesor)
        inputApellido = v.findViewById(R.id.inputModifApellidoProfesor)
        btnCreate = v.findViewById(R.id.btnCrearProfesor)
        btnBack = v.findViewById(R.id.btnVolverProfesor)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnBack.setOnClickListener {
            viewModelLista.recargarProfesores()
            v.findNavController().navigateUp()
        }

        btnCreate.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val apellido = inputApellido.text.toString()

            if (nombre.isEmpty()) {
                inputNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (apellido.isEmpty()) {
                inputApellido.error = "El apellido es obligatorio"
                return@setOnClickListener
            }


            val nuevoProfesor = Profesor(nombre, apellido)

            confirmAction("Crear") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.crearProfesor(nuevoProfesor) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Profesor creado exitosamente") {
                            viewModelLista.recargarProfesores()
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
        builder.setMessage("¿Estás seguro de que deseas $action este profesor?")

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
        viewModel = ViewModelProvider(this).get(CrearProfesorViewModel::class.java)
        viewModelLista = ViewModelProvider(requireActivity()).get(ProfesoresListaViewModel::class.java)
        // TODO: Use the ViewModel
    }
}