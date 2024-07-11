package com.example.cecytevlocationapp.data.Repository

import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.data.network.LoginService

class LoginRepository {
    private val api = LoginService()
    suspend fun login(login : LoginModel) : Int{
        var response = api.login(login)
        //RecipeProvider.recipeResponse.category = response.second.category
        LoginProvider.userCredentials = response.second
        return response.first
    }
}