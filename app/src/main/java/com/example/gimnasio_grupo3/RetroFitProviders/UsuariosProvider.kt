package com.example.gimnasio_grupo3.RetroFitProviders

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsuariosProvider {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://645ae28c95624ceb210d09ed.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}