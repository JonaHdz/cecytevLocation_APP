package com.example.cecytevlocationapp.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.LocationProvider
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityShowMenuParentBinding
import com.example.cecytevlocationapp.ui.viewModel.StudentLocationViewModel
import com.example.cecytevlocationapp.utility.AlertMessage
import com.example.cecytevlocationapp.utility.ChildAdapter
import com.example.cecytevlocationapp.utility.Codes

class ShowMenuParent : AppCompatActivity() {
    lateinit var binding : ActivityShowMenuParentBinding
    val childrenListViewModel : StudentLocationViewModel by viewModels()
    val alertMessage  = AlertMessage()
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
      when(childrenListViewModel.httpCodegetChildrenList){
          Codes.CODE_SUCCESS -> LoadRecyclerView()
          Codes.CODE_FAIL ->  alertMessage.showAlertDialog("Error de conexion","compuebe su conexion e intente mas tarde",this)
      }
    }

    private fun LoadRecyclerView() {
        if(!LocationProvider.childrenList.childrenListFiltered.isNullOrEmpty()){
            val adapter = ChildAdapter()
            adapter.contextShowMenuParent = this
            binding.rvChildListShowMenuParent.layoutManager = LinearLayoutManager(this)
            binding.rvChildListShowMenuParent.adapter = adapter
            for(item in LocationProvider.childrenList.childrenListFiltered){
                adapter.setItem(item)
            }
        }
    }
}