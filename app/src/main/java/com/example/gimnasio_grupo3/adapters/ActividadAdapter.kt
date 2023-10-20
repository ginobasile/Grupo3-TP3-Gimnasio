package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Actividad

class ActividadAdapter( var actividades : MutableList<Actividad>,
                        private val onItemClick: (Actividad) -> Unit
) : RecyclerView.Adapter<ActividadAdapter.ActividadHolder>() {

    class ActividadHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        val txtNombreActividad: TextView = itemView.findViewById(R.id.txtNombreActividad)
        val txtDuracion: TextView = itemView.findViewById(R.id.actividadDuracion)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actividad_item,parent,false)
        return (ActividadHolder(view))
    }

    override fun getItemCount(): Int {
        return actividades.size
    }

    override fun onBindViewHolder(holder: ActividadHolder, position: Int) {
        var actividad = actividades[position]

        holder.txtNombreActividad.text = actividad.name
        holder.txtDuracion.text = "Duración: ${actividad.duration} minutos"

        holder.itemView.setOnClickListener {
            onItemClick(actividad)
        }
    }
}

