package com.example.cecytevlocationapp.domain

import com.example.cecytevlocationapp.data.Repository.AttendaceRepository
import com.example.cecytevlocationapp.data.Repository.StudentLocationRepository
import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.LocationStudentModel

class registerStudentLocationUseCase {
    private val repository = StudentLocationRepository()
    suspend operator fun invoke(newLocation: LocationStudentModel): Int = repository.registerStudentLocation(newLocation)
}
