package com.example.gimnasio_grupo3.RetroFitProviders
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class ActividadesProvider {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://646937ca03bb12ac208876f1.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}