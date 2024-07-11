package com.example.cecytevlocationapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.domain.LoginUseCase
import com.example.cecytevlocationapp.domain.registerAttendanceUseCase
import kotlinx.coroutines.launch

class AttendanceViewModel  : ViewModel(){
    var httpCodeRegisterAttendance: Int = 400
    lateinit var attendance: AttendanceModel
    lateinit var  registerAttendanceUseCase: registerAttendanceUseCase
    val attendanceViewModel = MutableLiveData<Int>()

    fun registerAttendance() {
        viewModelScope.launch {
            registerAttendanceUseCase = registerAttendanceUseCase()
            val result = registerAttendanceUseCase(attendance) // Llama al caso de uso
            attendanceViewModel.postValue(result)
            httpCodeRegisterAttendance = result
        }
    }
}