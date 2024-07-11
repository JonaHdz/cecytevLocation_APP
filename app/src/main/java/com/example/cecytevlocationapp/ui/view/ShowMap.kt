package com.example.cecytevlocationapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.cecytevlocationapp.data.model.TutorCredencialsLocation
import com.example.cecytevlocationapp.databinding.ActivityShowMapBinding
import com.example.cecytevlocationapp.domain.GetStudentLocationUseCase
import com.example.cecytevlocationapp.ui.viewModel.StudentLocationViewModel

class ShowMap : AppCompatActivity() {
    lateinit var binding : ActivityShowMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWebView()
        loadLeafletMap()
        //

    }



    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.wbMap.webChromeClient = WebChromeClient()
        binding.wbMap.webViewClient = WebViewClient()

        val webSettings: WebSettings = binding.wbMap.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
    }

    private fun loadLeafletMap() {
        val htmlPath =
            "file:///android_asset/MapContainer.html"  // Ruta al archivo HTML de Leaflet en assets
        binding.wbMap.loadUrl(htmlPath)
    }


}