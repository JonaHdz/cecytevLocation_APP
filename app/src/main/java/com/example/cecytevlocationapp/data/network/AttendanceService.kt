package com.example.cecytevlocationapp.data.network

import com.example.cecytevlocationapp.core.RetrofitHelper
import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.AttendanceStudentResponse
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AttendanceService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun registerattendance(attendance : AttendanceModel): Pair<Int,AttendanceStudentResponse> {
        return withContext(Dispatchers.IO) {
            var code = 0
            var body = AttendanceStudentResponse()
            try {
                val response = retrofit.create(APIClient::class.java).registerAttendance(attendance)
                code = response.code()
                body = response.body() ?: AttendanceStudentResponse()
                Pair(code,body)
            } catch (e: Exception) {
                code = 500
                Pair(code,body)
            }

        }
    }
}