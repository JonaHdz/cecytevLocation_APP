package com.example.cecytevlocationapp.domain

import com.example.cecytevlocationapp.data.Repository.AttendaceRepository
import com.example.cecytevlocationapp.data.Repository.LoginRepository
import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.LoginModel

class registerAttendanceUseCase {
    private val repository = AttendaceRepository()
    suspend operator fun invoke(attendance: AttendanceModel): Int = repository.registerAttendance(attendance)
}