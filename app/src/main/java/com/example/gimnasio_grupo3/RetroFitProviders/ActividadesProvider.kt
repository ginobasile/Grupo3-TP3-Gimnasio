package com.example.gimnasio_grupo3.RetroFitProviders

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class ActividadesProvider {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://64662c65228bd07b355ddc69.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}