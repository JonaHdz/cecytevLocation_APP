package com.example.cecytevlocationapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.LoginProvider
import com.example.cecytevlocationapp.databinding.ActivityMenuTeacherBinding

class MenuTeacher : AppCompatActivity() {
    lateinit var binding : ActivityMenuTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        binding.etUsernameMenuTeacher.setText( LoginProvider.userCredentials.nameUser +" "+ LoginProvider.userCredentials.lastnameUser)
        binding.etUsernameMenuTeacher.isEnabled = false
    }

    private fun setListener() {
        binding.btnScanQR.setOnClickListener{
            val intent = Intent (this, ScanQR::class.java)
            startActivity(intent)
        }
        binding.btnExitMenuTeacher.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}