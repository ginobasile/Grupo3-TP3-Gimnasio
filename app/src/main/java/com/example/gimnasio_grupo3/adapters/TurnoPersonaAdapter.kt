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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TurnoPersonaAdapter(
    private val idUsuarioLogueado: Int,
    private val listaTurnosPersona: List<TurnoPersona>,
    private val listaTurnos: List<Turno>,
    private val listaActividades: List<Actividad>
) : RecyclerView.Adapter<TurnoPersonaAdapter.TurnoPersonaHolder>() {

    private var listaFiltrada: List<TurnoPersona>

    init {
        listaFiltrada = listaTurnosPersona.filter { it.idUsuario == idUsuarioLogueado }
        listaFiltrada = listaFiltrada.sortedBy { obtenerFechaPorIdTurno(it.idTurno) }
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
        private val txtPasado: TextView = itemView.findViewById(R.id.txtMsj)

        fun bindTurnoPersona(turnoPersona: TurnoPersona, nombreActividad: String) {
            textViewFecha.text = "Fecha: " + obtenerFechaPorIdTurno(turnoPersona.idTurno)
            textViewActividad.text = "Actividad: $nombreActividad"
            if (esFechaPasada(turnoPersona.idTurno)) { txtPasado.text = "Pasado" } else { txtPasado.text = "" }
        }
    }

    private fun obtenerIdActividadPorIdTurno(idTurno: Int): Int {
        val turno = listaTurnos.find { it.id == idTurno }
        return turno?.idActividad?.toInt() ?: -1
    }

    private fun obtenerNombreActividadPorId(idActividad: Int): String {
        val actividad = listaActividades.find { it.id == idActividad }
        return actividad?.name ?: "Actividad no encontrada"
    }

    private fun obtenerFechaPorIdTurno(idTurno: Int): String {
        val turno = listaTurnos.find { it.id == idTurno }
        return turno?.fecha ?: "Fecha no encontrada"
    }

    private fun esFechaPasada(idTurno: Int): Boolean {
        val fechaTurno = obtenerFechaPorIdTurno(idTurno)
        val fechaTurnoDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaTurno)
        val fechaActual = Date()
        return fechaTurnoDate != null && fechaTurnoDate < fechaActual
    }
}
