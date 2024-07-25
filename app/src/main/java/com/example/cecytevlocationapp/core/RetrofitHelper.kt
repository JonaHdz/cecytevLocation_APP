package com.example.cecytevlocationapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://412c-187-193-57-69.ngrok-free.app")//URL DE KNOR
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}