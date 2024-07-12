package com.example.cecytevlocationapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://a703-201-105-108-131.ngrok-free.app")//URL DE KNOR
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}