package com.example.gimnasio_grupo3.entities

class ActividadesRepository (){
    var actividades : MutableList<Actividad>
    init {
                actividades = mutableListOf()
                actividades.add(Actividad(1, "Boxeo", "75 Minutos"))
                actividades.add(Actividad(2, "Yoga", "30 Minutos"))
                actividades.add(Actividad(3, "Funcional", "60 Minutos"))
                actividades.add(Actividad(3, "Stretching", "65 Minutos"))
    }
}

