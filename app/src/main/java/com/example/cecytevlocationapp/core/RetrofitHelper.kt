package com.example.cecytevlocationapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://8565-201-105-190-121.ngrok-free.app")//URL DE KNOR
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}