package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Actividad


class ActividadAdapter( var actividades : MutableList<Actividad>
) : RecyclerView.Adapter<ActividadAdapter.ActividadHolder>() {

    class ActividadHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        private var view: View

        //private var view : View
        val txtNombreActividad: TextView = itemView.findViewById(R.id.txtNombrePaquete)
        val txtDuracion: TextView = itemView.findViewById(R.id.actividadDuracion)

        init {
           this.view = v
        }

        fun setActividadName( name : String) {
            val txtName : TextView = view.findViewById(R.id.txtNombrePaquete)
            txtName.text = name
        }




        //fun getCard() : CardView {
        //    return view.findViewById(R.id.cardActividad)
        //}

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
        holder.txtDuracion.text = "Precio: $${actividad.duration}"

        //val context = holder.itemView.context
        //Glide.with(context)
         //   .load(actividades[position].imgUrl)
         //   .circleCrop()
         //   .into(holder.itemImageView)
         //   holder.getCard().setOnClickListener{
         //   onClick(position)
        }
    }

