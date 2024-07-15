package com.example.cecytevlocationapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.LocationProvider
import com.example.cecytevlocationapp.data.model.TutorCredencialsLocation
import com.example.cecytevlocationapp.databinding.ActivityMainBinding
import com.example.cecytevlocationapp.databinding.ActivityTutorLoginBinding
import com.example.cecytevlocationapp.domain.GetStudentLocationUseCase
import com.example.cecytevlocationapp.ui.viewModel.StudentLocationViewModel
import com.example.cecytevlocationapp.utility.AlertMessage

class TutorLogin : AppCompatActivity() {
    lateinit var binding : ActivityTutorLoginBinding
    val getLocationStudentViewModel : StudentLocationViewModel by viewModels()

    var alertDialog = AlertMessage()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTutorLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        getLocationStudentViewModel.getstudentLocationViewModel.observe(this,{
            loadResponse()
        })
    }

    private fun loadResponse()  {
        binding.loadingLayerTutor.visibility = View.GONE
        binding.btnvalidateTutor.visibility = View.VISIBLE
        if (getLocationStudentViewModel.httpCodegetStudentLocation ==400)
            alertDialog.showAlertDialog("Error","No se encontró al estudiante, compruebe los datos de entrada",this);
        if(getLocationStudentViewModel.httpCodegetStudentLocation == 500){
            alertDialog.showAlertDialog("Error","Ocurrio un error. Favor de intentar mas tarde",this)
        }
        if (getLocationStudentViewModel.httpCodegetStudentLocation == 200){
            alertDialog.showAlertDialog("EXITO","ESTUDIANTE RECUPERADO:" + LocationProvider.locationStudent,this)
            var intent = Intent (this, ShowMap::class.java)
            startActivity(intent)
        }

    }

    private fun loadInfoTest() {
        binding.loadingLayerTutor.visibility = View.VISIBLE
        binding.btnvalidateTutor.visibility = View.GONE
        var credentials = TutorCredencialsLocation(
            idStudent = binding.etStudenIdTutor.text.toString(),
            telephoneTutor = binding.etTelephoneNumberTutor.text.toString()
        )
        getLocationStudentViewModel.credentials = credentials
        getLocationStudentViewModel.getStudentLocationUseCase = GetStudentLocationUseCase()
        getLocationStudentViewModel.getNewStudentLocation()
    }

    private fun validateInputs() {
        if(binding.etStudenIdTutor.text.isBlank() || binding.etTelephoneNumberTutor.text.isBlank()){
            alertDialog.showAlertDialog("Campos obligatorios", "Favor de llenar los campos",this)
        }else{
            loadInfoTest()
        }
    }

    private fun setListeners() {
       binding.btnvalidateTutor.setOnClickListener{
           validateInputs()
       }
    }


}