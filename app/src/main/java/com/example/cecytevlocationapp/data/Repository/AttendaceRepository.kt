package com.example.cecytevlocationapp.data.Repository

import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.AttendanceProvider
import com.example.cecytevlocationapp.data.model.AttendanceStudentResponse
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.data.network.AttendanceService
import com.example.cecytevlocationapp.data.network.LoginService

class AttendaceRepository {
    private val api = AttendanceService()
    suspend fun registerAttendance(attendance : AttendanceModel) : Int{
        var response = api.registerattendance(attendance)
        AttendanceProvider.studentAttendaceInfo =  response.second
        return response.first
    }
}