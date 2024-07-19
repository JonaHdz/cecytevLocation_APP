package com.example.cecytevlocationapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.AttendanceModel
import com.example.cecytevlocationapp.data.model.AttendanceProvider
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityScanQrBinding
import com.example.cecytevlocationapp.ui.viewModel.AttendanceViewModel
import com.google.zxing.integration.android.IntentIntegrator

class ScanQR : AppCompatActivity() {
    lateinit var binding : ActivityScanQrBinding
    val attendanceViewModel : AttendanceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        attendanceViewModel.attendanceViewModel.observe(this){
            checkCode()
        }
    }

    private fun checkCode() {
        if(attendanceViewModel.httpCodeRegisterAttendance == 200){
            showAlertDialog("Asistencia","Asistencia registrada\n\nNombre de estudiante: " +  AttendanceProvider.studentAttendaceInfo.nameStudent + " " +AttendanceProvider.studentAttendaceInfo.lastNameStudent
            + "\nMatricula de estudiante: " + AttendanceProvider.studentAttendaceInfo.idStudent)
        }else if(attendanceViewModel.httpCodeRegisterAttendance == 404){
            showAlertDialog("Error en QR","Estudiante no encontrado. Favor de verificar")
        }else{
            showAlertDialog("Error","Error de conexion. Favor de verificar su conexion o intente mas tarde")
        }
    }

    private fun setListener() {
        binding.btnScanQR.setOnClickListener{
            initScanner()
        }
        binding.btnExitScanQR.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Sigue aprendiendo en CursoKotlin.com")
        //integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Operacion Cancelada", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
                registerAttendance(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun registerAttendance(idStudent: String) {
        val newAttendance : AttendanceModel = AttendanceModel(
            typeAttendance = true,
            idStudent = idStudent,
            idTeacher = LoginProvider.userCredentials.idUser
        )
        attendanceViewModel.attendance = newAttendance
        attendanceViewModel.registerAttendance()
    }

    private fun showAlertDialog(title : String,message : String) {
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