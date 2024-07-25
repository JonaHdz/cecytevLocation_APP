package com.example.cecytevlocationapp.domain

import com.example.cecytevlocationapp.data.Repository.StudentLocationRepository

class GetChildrenListUseCase {
    private val repository = StudentLocationRepository()

    suspend operator fun invoke(telephone : String) : Int = repository.getChildrenList(telephone)
}