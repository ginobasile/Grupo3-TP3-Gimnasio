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
import androidx.navigation.findNavController

import com.example.gimnasio_grupo3.R
import com.google.android.material.snackbar.Snackbar

class DetalleUsuario : Fragment() {
    lateinit var v : View

    private lateinit var editDetallesNombre : EditText
    private lateinit var editDetallesMail : EditText
    private lateinit var editDetallesAltura : EditText
    private lateinit var editDetallesPese : EditText
    private lateinit var editDetallesEdad : EditText
    private lateinit var editDetallesContato : EditText

    private lateinit var btnDeleteUsuario: Button

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
        editDetallesMail = v.findViewById(R.id.editDetallesMail)
        editDetallesAltura = v.findViewById(R.id.editDetallesAltura)
        editDetallesPese = v.findViewById(R.id.editDetallesPese)
        editDetallesEdad = v.findViewById(R.id.editDetallesEdad)
        editDetallesContato = v.findViewById(R.id.editDetallesContato)

        btnDeleteUsuario = v.findViewById(R.id.btnDeleteUsuario)

        btnModUsuario = v.findViewById(R.id.btnModUsuario)


        return v


    }

    override fun onStart() {
        super.onStart()

        val usuario = DetalleUsuarioArgs.fromBundle(requireArguments()).usuario

        viewModel = ViewModelProvider(this).get(DetalleUsuarioViewModel::class.java)

        editDetallesNombre.setText("${usuario.nombre} ${usuario.apellido}");
        editDetallesMail.setText(usuario.mail);
        editDetallesAltura.setText(usuario.altura.toString());
        editDetallesPese.setText(usuario.peso.toString());
        editDetallesEdad.setText(usuario.edad.toString());
        editDetallesContato.setText(usuario.contacto);


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