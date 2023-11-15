package com.example.gimnasio_grupo3.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.entities.TurnoPersona
import com.example.gimnasio_grupo3.interfaces.APIMethods
import com.example.gimnasio_grupo3.sessions.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TurnoAdapter(
    context: Context,
    var turnos: MutableList<Turno>,
    var profesores: List<Profesor>,
    var actividades: List<Actividad>,
    private val onItemClick: (Turno) -> Unit,
) : RecyclerView.Adapter<TurnoAdapter.TurnoHolder>() {

    var myPreferences = MyPreferences(context)
    private var listaFiltrada: List<Turno>

    class TurnoHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtActividad: TextView = itemView.findViewById(R.id.txtActividadNombre)
        val txtProfesor: TextView = itemView.findViewById(R.id.txtProfesorNombre)
        val cantPersonas: TextView = itemView.findViewById(R.id.txtCantPersonas)
        val txtfecha: TextView = itemView.findViewById(R.id.txtFecha)
        val txtEstado: TextView = itemView.findViewById(R.id.textView4)
    }

    init {
        listaFiltrada = if (myPreferences.isAdmin()) {
            turnos.sortedByDescending {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.fecha)
            }
        } else {
            turnos
                .sortedByDescending {
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it.fecha)
                }
                .filter { turno -> !esFechaPasada(turno.fecha) }
        }

        Log.d("Carga", turnos.toString())
        Log.d("Carga", profesores.toString())
        Log.d("Carga", actividades.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.turno_item, parent, false)
        return TurnoHolder(view)
    }

    override fun getItemCount(): Int {
        return listaFiltrada.size
    }

    override fun onBindViewHolder(holder: TurnoHolder, position: Int) {
        val turno = listaFiltrada[position]

        Log.d("Probando2", actividades.toString())

        val actividad = actividades.find { it.id.toString().equals(turno.idActividad) }
        var actividadNombre = ""

        if (actividad != null) {
            actividadNombre = actividad.name
        } else {
            actividadNombre = "No encontrada"
        }

        val profesor = profesores.find { it.id.toString().equals(turno.idProfesor) }
        var profesorFullName = ""

        Log.d("TurnoProfesor", profesores.toString())

        if (profesor != null) {
            profesorFullName = "${profesor.nombre}, ${profesor.apellido}"
        } else {
            profesorFullName = "No encontrado"
        }

        holder.txtActividad.text = "Actividad: ${actividadNombre}"
        holder.txtProfesor.text = "Profesor: ${profesorFullName}"
        holder.cantPersonas.text = "Límite personas: ${turno.cantPersonasLim}"
        holder.txtfecha.text = "Fecha: ${turno.fecha}"

        if (esFechaPasada(turno.fecha)) {
            holder.txtEstado.text = "Pasado"
        } else {
            holder.txtEstado.text = ""
        }

        holder.itemView.setOnClickListener {
            onItemClick(turno)
        }
    }

    private fun esFechaPasada(fecha: String): Boolean {
        val fechaTurno = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fecha)
        val fechaActual = Date()
        return fechaTurno != null && fechaTurno < fechaActual
    }
}
