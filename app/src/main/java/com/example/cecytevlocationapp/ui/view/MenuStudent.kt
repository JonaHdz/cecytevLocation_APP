package com.example.cecytevlocationapp.ui.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityMenuStudentBinding
import com.example.cecytevlocationapp.utility.AlertMessage


class MenuStudent : AppCompatActivity() {
    lateinit var binding : ActivityMenuStudentBinding
    val alertDialog = AlertMessage()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMenuStudentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListener()

    }

    private fun setListener() {
        binding.etUsernameMenuStudent.setText(LoginProvider.userCredentials.nameUser + " " + LoginProvider.userCredentials.lastnameUser)
        binding.etUsernameMenuStudent.isEnabled = false
        binding.btnShowQR.setOnClickListener{
            val intent = Intent(this, ShowQRStudent::class.java)
            startActivity(intent)
        }
        binding.btnExitMenuStudent.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}