package com.example.cecytevlocationapp.ui.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityMainBinding
import com.example.cecytevlocationapp.ui.viewModel.LoginViewModel
import com.example.cecytevlocationapp.utility.AlertMessage
import com.example.cecytevlocationapp.utility.BackgroundLocationService
import com.example.cecytevlocationapp.utility.Codes
import com.example.cecytevlocationapp.utility.JobPosition
import com.example.cecytevlocationapp.utility.LocationService

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val location = BackgroundLocationService()
    private val alertDialog = AlertMessage()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListener()
        observeViewModel()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        validatePermissionLocation()

    }

    private fun validateInputs(): Boolean {
        if(binding.etUser.text.isBlank() || binding.etPassword.text.isBlank()){
            return false
        }
        return true
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            if (validateInputs()) {
                binding.btnSearchStudent.visibility = View.GONE
                login()
            }else{
                alertDialog.showAlertDialog("Error","ALguno de los campos se encuentra vacio. Favor de verificar",this)
            }
        }
        binding.btnSearchStudent.setOnClickListener{
            var intent = Intent(this, TutorLogin::class.java)
            startActivity(intent)
        }
        binding.btnExitLogin.setOnClickListener{
            finish()
        }
    }

    private fun login() {
        val login = LoginModel(
            user = binding.etUser.text.toString(),
            passwordUser= binding.etPassword.text.toString()
        )
        loginViewModel.loginModel = login
        loginViewModel.login()
        binding.loadinLayer.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.GONE
    }

    private fun observeViewModel() {
        loginViewModel.loginViewModel.observe(this) { result ->
            binding.loadinLayer.visibility = View.GONE
            binding.btnLogin.visibility = View.VISIBLE
            binding.btnSearchStudent.visibility = View.VISIBLE
            when (result) {
                Codes.CODE_SUCCESS -> handleLoginSuccess()
                Codes.CODE_NOT_FOUND -> showAlertDialog("Usuario no encontrado", "Matrícula o contraseña incorrecta. Favor de intentar de nuevo.")
                Codes.CODE_FAIL -> showAlertDialog("Error", "Error de conexión. Favor de revisar su conexión o intente más tarde.")
                else -> showAlertDialog("Error", "Ha ocurrido un error inesperado.")
            }
        }
    }

    private fun handleLoginSuccess() {
        when(LoginProvider.userCredentials.type){
            JobPosition.STUDENT -> {
                showStudentMenu()
                startBackgroundLocationService()
            }
            JobPosition.TEACHER -> showTeacherMenu()
            JobPosition.PARENT -> ShowParentMenu()
        }
    }

       private val PERMISSION_REQUEST_LOCATION = 1


       private fun validatePermissionLocation() {
           if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(
                   this,
                   arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                   PERMISSION_REQUEST_LOCATION
               )
           } else {
               Toast.makeText(this, "Permiso de ubicación recuperado" , Toast.LENGTH_SHORT).show()


           }
       }


       private fun startBackgroundLocationService() {
           val serviceIntent = Intent(this, BackgroundLocationService()::class.java)
          startService(serviceIntent)
       }

       // ACTIVA AL DAR PERMISO DE LOCALIZACION
       override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
           super.onRequestPermissionsResult(requestCode, permissions, grantResults)
           when (requestCode) {
               PERMISSION_REQUEST_LOCATION -> {
                   if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                       Toast.makeText(this, "EL PERMISO FUE ACEPTADO",Toast.LENGTH_SHORT).show()
                       // Permiso concedido, puedes acceder a la ubicación
                        val serviceIntent = Intent(this, BackgroundLocationService::class.java)
                        startService(serviceIntent)
                   } else {
                       // Permiso denegado, no puedes acceder a la ubicación
                       finish()
                   }
                   return
               }
           }
       }

    private fun showStudentMenu() {
        saveToSharedPreferences()
        val intent = Intent(this, MenuStudent::class.java)
        startActivity(intent)
        finish()
    }

    fun ShowParentMenu() {
        val intent = Intent(this,ShowMenuParent::class.java)
        startActivity(intent)
        finish()
    }


    private fun showTeacherMenu() {
        val intent = Intent(this, MenuTeacher::class.java)
        startActivity(intent)
        finish()
    }

    private fun startLocationService() {
        val serviceIntent = Intent(this, LocationService::class.java)
        //LocationService.enqueueWork(this, serviceIntent)
        Toast.makeText(this, "Servicio de ubicación iniciado", Toast.LENGTH_SHORT).show()
    }

    private fun saveToSharedPreferences() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("idStudent", LoginProvider.userCredentials.idUser)
        editor.apply()

        var idSudentSP = sharedPreferences.getString("idStudent","")
        Toast.makeText(this,"shared: " + idSudentSP, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("ACEPTAR") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}


