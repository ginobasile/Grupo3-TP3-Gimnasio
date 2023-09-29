package com.example.gimnasio_grupo3.entities

class ActividadesRepository (){
    var actividades : MutableList<Actividad>
    init {
                actividades = mutableListOf()
                actividades.add(Actividad(1, "Boxeo", "75 Minutos", "¡Ven y únete a nuestra emocionante clase de boxeo en el gimnasio! Experimenta la combinación perfecta de cardio, fuerza y destreza mientras te sumerges en el mundo del boxeo. Nuestros entrenadores altamente capacitados te guiarán a través de una sesión intensa y divertida que te ayudará a mejorar tu forma física, aumentar tu confianza y liberar el estrés acumulado. No importa si eres un principiante o un boxeador experimentado, esta actividad es adecuada para todos los niveles. Únete a nosotros para liberar tu energía, ponerte en forma y descubrir el guerrero interior que llevas dentro. ¡Te esperamos en el ring!", "https://i.etsystatic.com/34846232/r/il/5d1756/3978474298/il_570xN.3978474298_snzo.jpg "))
                actividades.add(Actividad(2, "Yoga", "30 Minutos", "Relájate y encuentra la paz interior en nuestras clases de yoga en el gimnasio. ¡Te invitamos a unirte a una experiencia de equilibrio y bienestar como ninguna otra!", "https://i.pinimg.com/originals/23/fa/08/23fa08dbc85fe652470d6a80473807b3.jpg"))
                actividades.add(Actividad(3, "Funcional", "60 Minutos", "¡Descubre tu potencial en nuestras sesiones de entrenamiento funcional en el gimnasio! Ven y alcanza tus objetivos de forma efectiva mientras te diviertes.", "https://i.pinimg.com/originals/87/e9/75/87e975ca9dfd3fc179f8c15f8c838628.jpg"))
                actividades.add(Actividad(3, "Stretching", "65 Minutos", "Mejora tu flexibilidad y alivia el estrés en nuestras clases de stretching en el gimnasio. ¡Únete a nosotros para sentirte más relajado y en sintonía con tu cuerpo!", "https://estaticos-cdn.prensaiberica.es/clip/1c17e101-cbd4-4369-a1f3-daee4f553c99_alta-aspect-ratio_default_0.jpg"))
    }
}

