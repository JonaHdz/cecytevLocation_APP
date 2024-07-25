package com.example.cecytevlocationapp.data.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.cecytevlocationapp.core.RetrofitHelper
import com.example.cecytevlocationapp.data.model.ChildInfo
import com.example.cecytevlocationapp.data.model.ChildrenListFiltered
import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.TelephoneParent
import com.example.cecytevlocationapp.data.model.TutorCredencialsLocation
import com.example.cecytevlocationapp.data.model.UserModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationStudentService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun registerStudentLocation(studentLocation: LocationStudentModel): Int {
        return withContext(Dispatchers.IO) {
            var code = 0
            try {
                val response =
                    retrofit.create(APIClient::class.java).registerLocationStudent(studentLocation)
                code = response.code()
                code
            } catch (e: Exception) {
                code = 500
                code
            }
        }
    }

    suspend fun getStudentLocation(credentials: TutorCredencialsLocation): Pair<Int, LocationStudentModel> {
        return withContext(Dispatchers.IO) {
            var code = 0
            var locationStudent: LocationStudentModel = LocationStudentModel()
            try {
                val response =
                    retrofit.create(APIClient::class.java).getLocationStudent(credentials)
                code = response.code()
                locationStudent = response.body() ?: LocationStudentModel()
                Pair(code, locationStudent)
            } catch (e: Exception) {
                code = 500
                Pair(code, locationStudent)
            }
        }
    }

    suspend fun getChildrenList(telephone: String): Pair<Int, ChildrenListFiltered> {
        return withContext(Dispatchers.IO) {
            var code = 0
            var childrenListFiltered: ChildrenListFiltered = ChildrenListFiltered()
            var telephoneParent : TelephoneParent = TelephoneParent(telephone)
            try {
                val response =
                    retrofit.create(APIClient::class.java).getChildrenList(telephoneParent)
                code = response.code()
                childrenListFiltered = response.body() ?: ChildrenListFiltered()
                Pair(code, childrenListFiltered)
            } catch (e: Exception) {
                code = 500
                Pair(code, childrenListFiltered)
            }
        }
    }
}