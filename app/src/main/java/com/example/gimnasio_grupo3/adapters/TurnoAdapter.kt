package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Turno

class TurnoAdapter(var turnos : MutableList<Turno>,
                       private val onItemClick: (Turno) -> Unit
) : RecyclerView.Adapter<TurnoAdapter.TurnoHolder>() {

    class TurnoHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        val txtActividad: TextView = itemView.findViewById(R.id.txtActividadNombre)
        val txtProfesor: TextView = itemView.findViewById(R.id.txtProfesorNombre)
        val cantPersonas: TextView = itemView.findViewById(R.id.txtCantPersonas)
        val txtfecha: TextView = itemView.findViewById(R.id.txtFecha)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.turno_item,parent,false)
        return (TurnoHolder(view))
    }

    override fun getItemCount(): Int {
        return turnos.size
    }

    override fun onBindViewHolder(holder: TurnoHolder, position: Int) {
        val turno = turnos[position]

        holder.txtActividad.text = "ID: ${turno.idActividad}"
        holder.txtProfesor.text = "Profesor: ${turno.idProfesor}"
        holder.cantPersonas.text = "Límite personas: ${turno.cantPersonasLim.toString()}"
        holder.txtfecha.text = "Fecha: ${turno.fecha.toString()}"

        holder.itemView.setOnClickListener {
            onItemClick(turno)
        }
    }
}