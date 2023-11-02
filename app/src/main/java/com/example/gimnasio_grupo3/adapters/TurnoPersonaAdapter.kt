package com.example.gimnasio_grupo3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona

class TurnoPersonaAdapter(
    private val idUsuarioLogueado: Int,
    private val listaTurnosPersona: List<TurnoPersona>,
    private val listaTurnos: List<Turno>,
    private val listaActividades: List<Actividad>
) : RecyclerView.Adapter<TurnoPersonaAdapter.TurnoPersonaHolder>() {

    private val listaFiltrada: List<TurnoPersona>

    init {
        // Filtrar la lista de TurnoPersona según el idUsuarioLogueado
        listaFiltrada = listaTurnosPersona.filter { it.idUsuario == idUsuarioLogueado }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnoPersonaHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_turno_persona, parent, false)
        return TurnoPersonaHolder(itemView)
    }

    override fun onBindViewHolder(holder: TurnoPersonaHolder, position: Int) {
        val turnoPersona = listaFiltrada[position]

        val idActividad = obtenerIdActividadPorIdTurno(turnoPersona.idTurno)
        val nombreActividad = obtenerNombreActividadPorId(idActividad)

        holder.bindTurnoPersona(turnoPersona, nombreActividad)
    }

    override fun getItemCount(): Int {
        return listaFiltrada.size
    }

    inner class TurnoPersonaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewFecha: TextView = itemView.findViewById(R.id.txtFecha)
        private val textViewActividad: TextView = itemView.findViewById(R.id.txtActividadNombre)

        fun bindTurnoPersona(turnoPersona: TurnoPersona, nombreActividad: String) {
            textViewFecha.text = "Fecha: " + obtenerFechaPorIdTurno(turnoPersona.idTurno)
            textViewActividad.text = "Actividad: $nombreActividad"
        }
    }

    private fun obtenerIdActividadPorIdTurno(idTurno: Int): Int {
        val turno = listaTurnos.find { it.id == idTurno }
        return turno?.idActividad?.toInt() ?: -1
    }

    private fun obtenerNombreActividadPorId(idActividad: Int): String {
        val actividad = listaActividades.find { it.id == idActividad }
        return actividad?.name ?: "Actividad no encontrada" // Devuelve un mensaje si no se encuentra la actividad
    }

    private fun obtenerFechaPorIdTurno(idTurno: Int): String {
        val turno = listaTurnos.find { it.id == idTurno }
        return turno?.fecha ?: "Fecha no encontrada" // Devuelve un mensaje si no se encuentra la fecha
    }
}