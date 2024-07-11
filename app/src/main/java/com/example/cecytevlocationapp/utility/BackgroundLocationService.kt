package com.example.cecytevlocationapp.utility

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BackgroundLocationService : Service() {
    @SuppressLint("MissingPermission")
     val TAG = "BackgroundLocation"
     val handler = Handler()
     val interval: Long = 1500  // 1.5 segundos

    // Variables de localización
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationManager: LocationManager
    private val locationRepository = LocationRepository()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Servicio iniciado en segundo plano")
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationRequest = LocationRequest.create().apply {
            interval = 15000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d("MyService", "Ubicación: ${location.longitude}, ${location.latitude}")
                    Toast.makeText(this@BackgroundLocationService, "Ubicación: ${location.longitude}, ${location.latitude}", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        val x = LocationStudentModel(
                            idStudent = "S123456",
                            dateLocation = "",
                            longitudeStudent = location.longitude.toString(),
                            latitudeStudent = location.latitude.toString()

                        )
                        locationRepository.sendLocation(x)
                    }
                }
            }


            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                if (!locationAvailability.isLocationAvailable) {
                    Log.d("MyService", "No se pudo obtener la ubicación.")
                }
            }
        }

        startLocationUpdates()
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Servicio detenido")
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
