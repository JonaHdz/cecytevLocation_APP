package com.example.cecytevlocationapp.utility

import android.util.Log
import com.example.cecytevlocationapp.core.RetrofitHelper
import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.example.cecytevlocationapp.data.network.APIClient

class LocationRepository {
    suspend fun sendLocation(newLocationStudent : LocationStudentModel){
        try {
            val retrofit = RetrofitHelper.getRetrofit()
            val apiService = retrofit.create(APIClient::class.java)
            val response = apiService.registerLocationStudent(newLocationStudent)
            Log.d("BackgroundLocation","Entrando en Repository service")
            if (response.isSuccessful) {
                Log.d("LocationRepository", "Ubicación enviada correctamente")
            } else {
                Log.e("LocationRepository", "Error al enviar la ubicación: ${response.code()}")
            }
        }catch (e : Exception ){
            Log.d("LocationRepository", "Excepcoin")
        }

    }
}