package com.example.gimnasio_grupo3.RetroFitProviders

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TurnosProvider {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://65244a19ea560a22a4e9b387.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}