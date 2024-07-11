package com.example.cecytevlocationapp.data.network

import com.example.cecytevlocationapp.core.RetrofitHelper
import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationStudentService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun registerStudentLocation(studentLocation : LocationStudentModel): Int {
        return withContext(Dispatchers.IO) {
            var code = 0
            try {
                val response = retrofit.create(APIClient::class.java).registerLocationStudent(studentLocation)
                code = response.code()
                code
            } catch (e: Exception) {
                code = 500
                code
            }

        }
    }
}