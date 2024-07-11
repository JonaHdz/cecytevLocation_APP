package com.example.cecytevlocationapp.data.network

import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.AttendanceStudentResponse
import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.TutorCredencialsLocation
import com.example.cecytevlocationapp.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIClient {
    @POST("api/v1/login")
    suspend fun login(@Body credentials : LoginModel): Response<UserModel>

    @POST("api/v1/registerattendance")
    suspend fun registerAttendance(@Body attendance : AttendanceModel): Response<AttendanceStudentResponse>
    @POST("api/v1/location/registerLocation")
    suspend fun registerLocationStudent(@Body locationStudent : LocationStudentModel): Response<Void>
    @POST("api/v1/location/getLocationStudent")
    suspend fun getLocationStudent(@Body locationStudent : TutorCredencialsLocation): Response<LocationStudentModel>
}