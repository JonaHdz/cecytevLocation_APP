package com.example.cecytevlocationapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    var httpCodeLogin: Int = 400
    lateinit var loginModel: LoginModel
    lateinit var  loginUseCase: LoginUseCase
    val loginViewModel = MutableLiveData<Int>()

    fun login() {
        viewModelScope.launch {
            loginUseCase = LoginUseCase()
            val result = loginUseCase(loginModel) // Llama al caso de uso
            loginViewModel.postValue(result)
            httpCodeLogin = result
        }
    }
}