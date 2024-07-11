package com.example.cecytevlocationapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.example.cecytevlocationapp.domain.registerAttendanceUseCase
import com.example.cecytevlocationapp.domain.registerStudentLocationUseCase
import kotlinx.coroutines.launch

class StudentLocationViewModel :ViewModel(){
    var httpCodeRegisterStudentLocation: Int = 400
    lateinit var newStudentLocation: LocationStudentModel
    lateinit var  registerStudentLocationUseCase: registerStudentLocationUseCase
    val studentLocationViewModel = MutableLiveData<Int>()

    fun registerNewStudentLocation() {
        viewModelScope.launch {
            registerStudentLocationUseCase = registerStudentLocationUseCase()
            val result = registerStudentLocationUseCase(newStudentLocation) // Llama al caso de uso
            studentLocationViewModel.postValue(result)
            httpCodeRegisterStudentLocation = result
        }
    }
}