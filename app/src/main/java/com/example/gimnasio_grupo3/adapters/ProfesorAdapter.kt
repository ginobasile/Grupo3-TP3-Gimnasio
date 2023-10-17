package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Paquete


class ProfesorAdapter(var profesores : MutableList<Profesor>,
                      private val onItemClick: (Profesor) -> Unit
) : RecyclerView.Adapter<ProfesorAdapter.ProfesorHolder>() {

    class ProfesorHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        val txtNombreProfesor: TextView = itemView.findViewById(R.id.txtNombreProfesor)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profesor_item,parent,false)
        return (ProfesorHolder(view))
    }

    override fun getItemCount(): Int {
        return profesores.size
    }

    override fun onBindViewHolder(holder: ProfesorHolder, position: Int) {
        var profesor = profesores[position]

        holder.txtNombreProfesor.text = profesor.nombre


        holder.itemView.setOnClickListener {
            onItemClick(profesor)
        }
    }
}

