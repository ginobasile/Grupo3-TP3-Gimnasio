package com.example.gimnasio_grupo3.fragments.loginFragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.navigation.findNavController
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Usuario
import com.google.android.material.snackbar.Snackbar

class CrearUsuario : Fragment() {
    lateinit var v : View
    private lateinit var editDetallesNombre : EditText
    private lateinit var editDetallesApellido : EditText
    private lateinit var editDetallesMail : EditText
    private lateinit var editDetallesContraseña : EditText
    private lateinit var editDetallesAltura : EditText
    private lateinit var editDetallesPeso : EditText
    private lateinit var editDetallesEdad : EditText
    private lateinit var editDetallesContacto : EditText
    private lateinit var editDetallesDni : EditText
    private lateinit var editDetallesTickets : EditText

    private lateinit var btnCreate: Button
    private lateinit var btnBack: Button
    companion object {
        fun newInstance() = CrearUsuario()
    }

    private lateinit var viewModel: CrearUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_crear_usuario, container, false)

        editDetallesNombre = v.findViewById(R.id.editDetallesNombre)
        editDetallesApellido = v.findViewById(R.id.editDetallesApellido)
        editDetallesMail = v.findViewById(R.id.editDetallesMail)
        editDetallesContraseña = v.findViewById(R.id.editDetallesContraseña)
        editDetallesAltura = v.findViewById(R.id.editDetallesAltura)
        editDetallesPeso = v.findViewById(R.id.editDetallesPeso)
        editDetallesEdad = v.findViewById(R.id.editDetallesEdad)
        editDetallesContacto = v.findViewById(R.id.editDetallesContacto)
        editDetallesDni = v.findViewById(R.id.editDetallesDni)

        btnCreate = v.findViewById(R.id.btnCreate)
        btnBack = v.findViewById(R.id.btnVolver)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnBack.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnCreate.setOnClickListener {

            val nuevoNombre = editDetallesNombre.text.toString()
            val nuevoApellido = editDetallesApellido.text.toString()
            val nuevoMail = editDetallesMail.text.toString()
            val nuevaContraseña = editDetallesContraseña.text.toString()
            val nuevaAltura = editDetallesAltura.text.toString()
            val nuevoPeso = editDetallesPeso.text.toString()
            val nuevaEdad = editDetallesEdad.text.toString()
            val nuevoContacto = editDetallesContacto.text.toString()
            val nuevoDni = editDetallesDni.text.toString()

            if (nuevoNombre.isEmpty()) {
                editDetallesNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (nuevoApellido.isEmpty()) {
                editDetallesApellido.error = "El apellido es obligatorio"
                return@setOnClickListener
            }

            if (nuevoMail.isEmpty()) {
                editDetallesMail.error = "El mail es obligatorio"
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(nuevoMail).matches()) {
                editDetallesMail.error = "El formato del mail no es correcto"
                return@setOnClickListener
            }

            if (nuevaContraseña.isEmpty()) {
                editDetallesContraseña.error = "La contraseña es obligatoria"
                return@setOnClickListener
            }

            if (nuevaContraseña.length < 8) {
                editDetallesContraseña.error = "La contraseña debe tener al menos 8 caracteres"
                return@setOnClickListener
            }

            if (!nuevaContraseña.contains(Regex("[A-Z]"))) {
                editDetallesContraseña.error = "La contraseña debe contener al menos una letra mayúscula"
                return@setOnClickListener
            }

            if (!Regex("[0-9]").containsMatchIn(nuevaContraseña)) {
                editDetallesContraseña.error = "La contraseña debe contener al menos un número"
                return@setOnClickListener
            }

            if (nuevaAltura.isEmpty()) {
                editDetallesAltura.error = "La altura es obligatoria"
                return@setOnClickListener
            }

            if (nuevaAltura.toInt() < 0 || nuevaAltura.toInt() > 300) {
                editDetallesAltura.error = "La altura debe estar entre 0 y 300 cm"
                return@setOnClickListener
            }

            if (nuevoPeso.isEmpty()) {
                editDetallesPeso.error = "El peso es obligatorio"
                return@setOnClickListener
            }

            if (nuevoPeso.toInt() < 0 || nuevoPeso.toInt() > 400) {
                editDetallesPeso.error = "El peso debe estar entre 0 y 400 kg"
                return@setOnClickListener
            }

            if (nuevaEdad.isEmpty()) {
                editDetallesEdad.error = "La edad es obligatoria"
                return@setOnClickListener
            }

            if (nuevaEdad.toInt() < 0 || nuevaEdad.toInt() > 100) {
                editDetallesEdad.error = "La edad debe estar entre 0 y 100 años"
                return@setOnClickListener
            }

            if (nuevoContacto.isEmpty()) {
                editDetallesContacto.error = "El contacto es obligatorio"
                return@setOnClickListener
            }

            if (nuevoDni.isEmpty()) {
                editDetallesDni.error = "El dni es obligatorio"
                return@setOnClickListener
            }

            val nuevoUsuario = Usuario(
                nuevoNombre,
                nuevoApellido,
                nuevoMail,
                nuevaContraseña,
                nuevaAltura.toInt(),
                nuevoPeso.toInt(),
                nuevaEdad.toInt(),
                nuevoContacto,
                false,
                nuevoDni.toInt(),
                0
            )

            confirmAction("Crear") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.crearUsuario(nuevoUsuario) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Usuario creado exitosamente") {
                            v.findNavController().navigateUp()
                        }
                    }
                } else {
                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CrearUsuarioViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun confirmAction(action: String, callback: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(action)
        builder.setMessage("¿Estás seguro de que deseas $action esta cuenta?")

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