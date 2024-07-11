package com.example.cecytevlocationapp.domain

import com.example.cecytevlocationapp.data.Repository.StudentLocationRepository
import com.example.cecytevlocationapp.data.model.TutorCredencialsLocation

class GetStudentLocationUseCase {
    private val repository = StudentLocationRepository()

    suspend operator fun invoke(credentials: TutorCredencialsLocation): Int = repository.getStudentLocation(credentials)
}