package com.example.cecytevlocationapp.domain

import com.example.cecytevlocationapp.data.Repository.LoginRepository
import com.example.cecytevlocationapp.data.model.LoginModel

class LoginUseCase {
    private val repository = LoginRepository()

    suspend operator fun invoke(login: LoginModel): Int = repository.login(login)
}