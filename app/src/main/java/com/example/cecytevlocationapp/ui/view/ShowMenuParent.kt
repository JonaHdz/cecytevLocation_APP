package com.example.cecytevlocationapp.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.LocationProvider
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityShowMenuParentBinding
import com.example.cecytevlocationapp.ui.viewModel.StudentLocationViewModel

class ShowMenuParent : AppCompatActivity() {
    lateinit var binding : ActivityShowMenuParentBinding
    val childrenListViewModel : StudentLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityShowMenuParentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        childrenListViewModel.telephone = LoginProvider.userCredentials.idUser
        Log.d("testService","antes de llamada")
        childrenListViewModel.getChildrenList()
        childrenListViewModel.getChildrenListViewModel.observe(this){
            validateCode()
        }
       // Toast.makeText(this,"salto",Toast.LENGTH_LONG).show()
    }

    private fun validateCode() {
        var x  = LocationProvider.childrenList.childrenListFiltered.count()
        Toast.makeText(this,"codigo" + childrenListViewModel.httpCodegetChildrenLocation,Toast.LENGTH_LONG).show()
    }
}