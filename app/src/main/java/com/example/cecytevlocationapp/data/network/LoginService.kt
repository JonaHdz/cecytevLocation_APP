package com.example.cecytevlocationapp.data.network

import com.example.cecytevlocationapp.core.RetrofitHelper
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun login(login : LoginModel): Pair<Int, UserModel> {
        return withContext(Dispatchers.IO) {
            var code = 0
            var body: UserModel
            try {
                val response = retrofit.create(APIClient::class.java).login(login)
                code = response.code()
                body = response.body() ?: UserModel()
                Pair(code, body)
            } catch (e: Exception) {
                code = 500
                Pair(code, UserModel())
            }

        }
    }
}