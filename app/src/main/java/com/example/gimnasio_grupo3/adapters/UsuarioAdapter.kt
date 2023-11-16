package com.example.gimnasio_grupo3.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.Firebase.FirebaseStorageConnection
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Usuario

class UsuarioAdapter(
    val usuarios : MutableList<Usuario>,
    private val onItemClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioHolder>() {

    class UsuarioHolder(v : View) : RecyclerView.ViewHolder(v) {

        private var view : View
        init {
            this.view = v
        }

        fun setNombreCompleto(nombre: String, apellido: String){
            val txtNombreApellido: TextView = view.findViewById(R.id.txtNombreApellido)
            val txt = "${apellido}, ${nombre}"
            txtNombreApellido.setText(txt)
        }

        fun isAdmin(admin: Boolean){
            val brandTxtView : TextView = view.findViewById(R.id.txtAdmin)

            if (!admin){
                val txt = ""
                brandTxtView.setText(txt)
            }
        }

        fun setDni(dni: String){
            val txtDni: TextView = view.findViewById(R.id.txtDniEnUsuario)
            val txt = "DNI: ${dni}"
            txtDni.setText(txt)
        }

        fun setImage(id:String){
            val firebaseStorageConnection = FirebaseStorageConnection()
            val img : ImageView = view.findViewById(R.id.imgUsuario)
            firebaseStorageConnection.getImageLoadingAndImgDefault(img, "usuarios/${id}.jpg")

        }

        fun setTickets(tickets: String){
            val txtTickets: TextView = view.findViewById(R.id.txtTicketsEnUsuario)
            val txt = "Tickets: ${tickets}"
            txtTickets.setText(txt)
        }

        fun setId(id: String){
            val txtId: TextView = view.findViewById(R.id.shimmerId)
            val txt = "ID: ${id}"
            txtId.setText(txt)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.usuario_item,parent,false)
        return (UsuarioHolder(view))
    }

    override fun getItemCount(): Int = this.usuarios.size

    override fun onBindViewHolder(holder: UsuarioHolder, position: Int) {
        val usuario = this.usuarios[position]

        holder.setNombreCompleto(usuario.nombre, usuario.apellido)
        holder.isAdmin(usuario.administrador)
        holder.setDni(usuario.dni.toString())
        holder.setTickets(usuario.ticketsRestantes.toString())
        holder.setId(usuario.id.toString())
        holder.setImage(usuario.id.toString())

        holder.itemView.setOnClickListener {
            onItemClick(usuario)
        }
    }

}