package com.example.gimnasio_grupo3.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasio_grupo3.R
import com.example.gimnasio_grupo3.RetroFitProviders.ActividadesProvider
import com.example.gimnasio_grupo3.RetroFitProviders.ProfesoresProvider
import com.example.gimnasio_grupo3.entities.Actividad
import com.example.gimnasio_grupo3.entities.Profesor
import com.example.gimnasio_grupo3.entities.Turno
import com.example.gimnasio_grupo3.interfaces.APIMethods
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TurnoAdapter(
    var turnos: MutableList<Turno>,
    private val onItemClick: (Turno) -> Unit
) : RecyclerView.Adapter<TurnoAdapter.TurnoHolder>() {

    var actividadesList = mutableListOf<Actividad>()
    var profesoresList = mutableListOf<Profesor>()

    class TurnoHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtActividad: TextView = itemView.findViewById(R.id.txtActividadNombre)
        val txtProfesor: TextView = itemView.findViewById(R.id.txtProfesorNombre)
        val cantPersonas: TextView = itemView.findViewById(R.id.txtCantPersonas)
        val txtfecha: TextView = itemView.findViewById(R.id.txtFecha)
    }

    init {
        // Llama a la función obtenerActividades para obtener la lista de actividades al construir el adaptador.
        obtenerActividades { actividades ->
            actividades?.let {
                actividadesList.clear()
                actividadesList.addAll(it)
                notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado.
            }
        }

        // Llama a la función obtenerProfesores para obtener la lista de profesores al construir el adaptador.
        obtenerProfesores { profesores ->
            profesores?.let {
            }
        }
    }

    fun obtenerProfesores(callback: (List<Profesor>?) -> Unit) {
        val retrofit = ProfesoresProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getProfesores()

        call.enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(
                call: Call<List<Profesor>>,
                response: Response<List<Profesor>>
            ) {
                if (response.isSuccessful) {
                    val profesoresLista = response.body()
                    Log.d("hola", profesoresLista.toString())
                    if (profesoresLista != null) {
                        profesoresList = profesoresLista.toMutableList()
                    }
                    callback(profesoresList)
                } else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }

    fun obtenerActividades(callback: (List<Actividad>?) -> Unit) {
        val retrofit = ActividadesProvider().provideRetrofit()
        val apiService = retrofit.create(APIMethods::class.java)
        val call = apiService.getActividad()

        call.enqueue(object : Callback<List<Actividad>> {
            override fun onResponse(
                call: Call<List<Actividad>>,
                response: Response<List<Actividad>>
            ) {
                if (response.isSuccessful) {
                    val actividadesLista = response.body()
                    if (actividadesLista != null) {
                        actividadesList = actividadesLista.toMutableList()
                    }
                    callback(actividadesLista)
                } else {
                    // La llamada no fue exitosa, maneja los errores aquí
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Actividad>>, t: Throwable) {
                // Maneja errores de conexión aquí
                callback(null)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.turno_item, parent, false)
        return TurnoHolder(view)
    }

    override fun getItemCount(): Int {
        return turnos.size
    }

    override fun onBindViewHolder(holder: TurnoHolder, position: Int) {
        val turno = turnos[position]

        val actividad = actividadesList.find { it.id.toString().equals(turno.idActividad) }
        var actividadNombre = ""
        if(actividad != null) {
            actividadNombre = actividad.name
        }

        val profesor = profesoresList.find { it.id.toString().equals(turno.idProfesor) }
        var profesorFullName = ""
        if(profesor != null) {
            profesorFullName = "${profesor.nombre}, ${profesor.apellido}"
        }

        holder.txtActividad.text = "Actividad: ${actividadNombre}"
        holder.txtProfesor.text = "Profesor: ${profesorFullName}"
        holder.cantPersonas.text = "Límite personas: ${turno.cantPersonasLim}"
        holder.txtfecha.text = "Fecha: ${turno.fecha}"

        holder.itemView.setOnClickListener {
            onItemClick(turno)
        }
    }
}
