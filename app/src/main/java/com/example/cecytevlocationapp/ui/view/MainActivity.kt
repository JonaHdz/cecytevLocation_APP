package com.example.cecytevlocationapp.ui.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.LoginModel
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityMainBinding
import com.example.cecytevlocationapp.ui.viewModel.LoginViewModel
import com.example.cecytevlocationapp.utility.BackgroundLocationService
import com.example.cecytevlocationapp.utility.LocationService
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    //private val location = BackgroundLocationService(this)

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
        return true
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            if (validateInputs()) {
                login()
            }
        }
        binding.btnSearchStudent.setOnClickListener{
            var intent = Intent(this, TutorLogin::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val login = LoginModel(
            idStudent = binding.etUser.text.toString(),
            passwordStudent = binding.etPassword.text.toString()
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
            when (result) {
                200 -> handleLoginSuccess()
                404 -> showAlertDialog("Usuario no encontrado", "Matrícula o contraseña incorrecta. Favor de intentar de nuevo.")
                400 -> showAlertDialog("Error", "Error de conexión. Favor de revisar su conexión o intentar más tarde.")
                else -> showAlertDialog("Error", "Ha ocurrido un error inesperado.")
            }
        }
    }

    private fun handleLoginSuccess() {
        if (LoginProvider.userCredentials.type == "student") {
            // METER EN UN IF LA PANTALLA DE STUDENT PARA VERIFICAR EL PERMISO
            showStudentMenu()
        } else {
            showTeacherMenu()
        }
    }

       private val PERMISSION_REQUEST_LOCATION = 1


       private fun validatePermissionLocation() {
           if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(this, "Pidiendo permiso de ubicación", Toast.LENGTH_SHORT).show()
               ActivityCompat.requestPermissions(
                   this,
                   arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                   PERMISSION_REQUEST_LOCATION
               )
           } else {
               Toast.makeText(this, "El permiso de ubicación ya lo tenía", Toast.LENGTH_SHORT).show()


           }
       }


   /*    private fun startBackgroundLocationService() {
           val serviceIntent = Intent(this, BackgroundLocationService(this)::class.java)
           startService(serviceIntent)
       }*/

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
                       Toast.makeText(this,"kjbasdkjads",Toast.LENGTH_SHORT).show()
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

    /*
    private val LOCATION_PERMISSIONS_REQUEST_CODE = 1001

    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
        )

        val permissionsNeeded = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), LOCATION_PERMISSIONS_REQUEST_CODE)
        } else {
            // Todos los permisos están concedidos, puedes iniciar el servicio en primer plano aquí.
            startBackgroundLocationService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this,"PERMISO ACEPTADO",Toast.LENGTH_SHORT).show()
                // Todos los permisos están concedidos, puedes iniciar el servicio en primer plano aquí.
                startBackgroundLocationService()
            } else {
                // Manejar el caso donde los permisos no fueron concedidos.
                Toast.makeText(this,"PERMISO RECHAZADO",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startBackgroundLocationService() {
        Toast.makeText(this,"adsadsadsadsads",Toast.LENGTH_SHORT).show()
        Log.e("DESDE MAIN", "FUNCION ANTES DEL SERVICE")
        val intent = Intent(this, BackgroundLocationService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }
*/
}

