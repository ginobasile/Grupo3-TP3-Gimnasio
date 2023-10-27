package com.example.gimnasio_grupo3.fragments.usuarios

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
import com.example.gimnasio_grupo3.entities.Usuario
import com.google.android.material.snackbar.Snackbar

class DetalleUsuario : Fragment() {
    lateinit var v : View

    private lateinit var txtId : TextView
    private lateinit var editDetallesNombre : EditText
    private lateinit var editDetallesApellido : EditText
    private lateinit var editDetallesMail : EditText
    private lateinit var editDetallesContraseña : EditText
    private lateinit var editDetallesAltura : EditText
    private lateinit var editDetallesPeso : EditText
    private lateinit var editDetallesEdad : EditText
    private lateinit var editDetallesContacto : EditText
    private lateinit var editDetallesAdmin: EditText
    private lateinit var editDetallesDni : EditText
    private lateinit var editDetallesTickets : EditText

    private lateinit var btnDeleteUsuario: Button
    private lateinit var btnVolver : Button
    private lateinit var btnModUsuario: Button


    companion object {
        fun newInstance() = DetalleUsuario()
    }

    private lateinit var viewModel: DetalleUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_usuario, container, false)

        editDetallesNombre = v.findViewById(R.id.editDetallesNombre)
        editDetallesApellido = v.findViewById(R.id.editDetallesApellido)
        editDetallesMail = v.findViewById(R.id.editDetallesMail)
        editDetallesContraseña = v.findViewById(R.id.editDetallesContraseña)
        editDetallesAltura = v.findViewById(R.id.editDetallesAltura)
        editDetallesPeso = v.findViewById(R.id.editDetallesPeso)
        editDetallesEdad = v.findViewById(R.id.editDetallesEdad)
        editDetallesContacto = v.findViewById(R.id.editDetallesContacto)
        editDetallesAdmin = v.findViewById(R.id.editDetallesAdmin)
        editDetallesDni = v.findViewById(R.id.editDetallesDni)
        editDetallesTickets = v.findViewById(R.id.editDetallesTickets)

        txtId = v.findViewById(R.id.textViewIDActividad2)

        btnDeleteUsuario = v.findViewById(R.id.btnDeleteUsuario)
        btnVolver = v.findViewById(R.id.btnVolver)
        btnModUsuario = v.findViewById(R.id.btnModUsuario)

        return v
    }

    override fun onStart() {
        super.onStart()

        val usuario = DetalleUsuarioArgs.fromBundle(requireArguments()).usuario
        txtId.text = "Id: ${usuario.id}"
        viewModel = ViewModelProvider(this).get(DetalleUsuarioViewModel::class.java)

        editDetallesNombre.setText(usuario.nombre);
        editDetallesApellido.setText(usuario.apellido);
        editDetallesMail.setText(usuario.mail);
        editDetallesContraseña.setText(usuario.password);
        editDetallesAltura.setText(usuario.altura.toString());
        editDetallesPeso.setText(usuario.peso.toString());
        editDetallesEdad.setText(usuario.edad.toString());
        editDetallesContacto.setText(usuario.contacto);
        editDetallesAdmin.setText(usuario.administrador.toString());
        editDetallesDni.setText(usuario.dni.toString());
        editDetallesTickets.setText(usuario.ticketsRestantes.toString());

        btnVolver.setOnClickListener {
            v.findNavController().navigateUp()
        }

        btnModUsuario.setOnClickListener {
            val nuevoNombre = editDetallesNombre.text.toString()
            val nuevoApellido = editDetallesApellido.text.toString()
            val nuevoMail = editDetallesMail.text.toString()
            val nuevaContraseña = editDetallesContraseña.text.toString()
            val nuevaAltura = editDetallesAltura.text.toString()
            val nuevoPeso = editDetallesPeso.text.toString()
            val nuevaEdad = editDetallesEdad.text.toString()
            val nuevoContacto = editDetallesContacto.text.toString()
            val esAdministrador = editDetallesAdmin.text.toString()
            val nuevoDni = editDetallesDni.text.toString()
            val nuevosTickets = editDetallesTickets.text.toString()

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
                editDetallesEdad.error = "El peso es obligatorio"
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

            if (nuevosTickets.isEmpty()) {
                editDetallesTickets.error = "Los tickets son obligatorios"
                return@setOnClickListener
            }

            if (nuevosTickets.toInt() < 0) {
                editDetallesTickets.error = "Los tickets deben ser mayor a 0"
                return@setOnClickListener
            }

            val usuarioActualizado = Usuario(
                usuario.id,
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
                nuevosTickets.toInt()
            )

            confirmAction("Modificar") { confirmed ->
                if (confirmed) {
                    // Llama a la función en el ViewModel y pasa un callback
                    viewModel.actualizarUsuario(usuarioActualizado) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if (estado == "Usuario actualizado exitosamente") {
                            v.findNavController().navigateUp()
                        }
                    }
                } else {
                    Snackbar.make(v, "Acción cancelada", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        btnDeleteUsuario.setOnClickListener {
            confirmAction("Eliminar") { confirmed ->
                if (confirmed) {
                    viewModel.eliminarUsuario(usuario) { estado ->
                        Snackbar.make(v, estado, Snackbar.LENGTH_LONG).show()

                        if(estado == "Usuario eliminado exitosamente"){
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
        builder.setMessage("¿Estás seguro de que deseas $action este Usuario?")

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