package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Paquete

class PaqueteAdapter(var paquetes : MutableList<Paquete>
) : RecyclerView.Adapter<PaqueteAdapter.PaqueteHolder>() {

    class PaqueteHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        val txtNombrePaquete: TextView = itemView.findViewById(R.id.txtNombrePaquete)
        val txtPrecioPaquete: TextView = itemView.findViewById(R.id.txtPrecioPaquete)
        val txtTickets: TextView = itemView.findViewById(R.id.txtTickets)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaqueteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paquete_item,parent,false)
        return (PaqueteHolder(view))
    }

    override fun getItemCount(): Int {
        return paquetes.size
    }

    override fun onBindViewHolder(holder: PaqueteHolder, position: Int) {
        var paquete = paquetes[position]

        holder.txtNombrePaquete.text = paquete.nombre
        holder.txtPrecioPaquete.text = "Precio: $${paquete.precio}"
        holder.txtTickets.text = "Cantidad de tickets: ${paquete.cantTickets}"
    }
}