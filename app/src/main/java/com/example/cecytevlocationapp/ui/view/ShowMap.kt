package com.example.cecytevlocationapp.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.cecytevlocationapp.data.model.LocationProvider
import com.example.cecytevlocationapp.data.model.TutorCredencialsLocation
import com.example.cecytevlocationapp.databinding.ActivityShowMapBinding
import com.example.cecytevlocationapp.domain.GetStudentLocationUseCase
import com.example.cecytevlocationapp.ui.viewModel.StudentLocationViewModel

class ShowMap : AppCompatActivity() {
    lateinit var binding: ActivityShowMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //recupera info del hijo
        setupWebView()
        loadLeafletMap()
        setListeners()
    }

    private fun setListeners() {
        binding.btnExitShowMap.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
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
        val longitude = LocationProvider.locationStudent.longitudeStudent
        val latitude = LocationProvider.locationStudent.latitudeStudent
        val x = longitude
        val y = latitude
        val htmlPath = "file:///android_asset/MapContainer.html"  // Ruta al archivo HTML de Leaflet en assets
        binding.wbMap.loadUrl(htmlPath)

        val javascript = "javascript:addMarker($latitude, $longitude);"
        binding.wbMap.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(javascript, null)
            }
        }
    }

}