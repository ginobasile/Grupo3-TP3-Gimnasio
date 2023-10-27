package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Usuario

class UsuarioAdapter(
    val usuarios : MutableList<Usuario>,
    private val onItemClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder>() {

    class UsuarioHolder(v : View) : RecyclerView.ViewHolder(v) {

        private var view : View;
        init {
            this.view = v;
        }

        fun setNombreCompleto(nombre: String, apellido: String){
            val txtNombreApellido: TextView = view.findViewById(R.id.txtNombreApellido)
            txtNombreApellido.text = "${apellido}, ${nombre}"
        }

        fun setMail(mail: String){
            val txtEmail: TextView = view.findViewById(R.id.txtEmail)
            txtEmail.text = "Mail: ${mail}"
        }

        fun setContacto(contacto: String){
            val txtContacto: TextView = view.findViewById(R.id.txtContacto)
            txtContacto.text = "Contacto: ${contacto}"
        }

        fun isAdmin(admin: Boolean){
            val brandTxtView : TextView = view.findViewById(R.id.txtAdmin)

            if (!admin){
                brandTxtView.text = "";
            }
        }

        fun setDni(dni: String){
            val txtDni: TextView = view.findViewById(R.id.txtEmail2)
            txtDni.text =  "DNI: ${dni}"
        }

        fun setTickets(tickets: String){
            val txtTickets: TextView = view.findViewById(R.id.txtEmail3)
            txtTickets.text = "Tickets: ${tickets}"
        }

        fun setId(id: String){
            val txtId: TextView = view.findViewById(R.id.textView6)
            txtId.text = "ID: ${id}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usuario_item,parent,false)
        return (UsuarioAdapter.UsuarioHolder(view))
    }

    override fun getItemCount(): Int = this.usuarios.size

    override fun onBindViewHolder(holder: UsuarioHolder, position: Int) {
        val usuario = this.usuarios[position]

        holder.setNombreCompleto(usuario.nombre, usuario.apellido)
        holder.setMail(usuario.mail)
        holder.setContacto(usuario.contacto)
        holder.isAdmin(usuario.administrador)
        holder.setDni(usuario.dni.toString())
        holder.setTickets(usuario.ticketsRestantes.toString())
        holder.setId(usuario.id.toString())

        holder.itemView.setOnClickListener {
            onItemClick(usuario)
        }
    }

}