package com.example.cecytevlocationapp.utility

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.LocationManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.LocationStudentModel
import com.example.cecytevlocationapp.ui.view.MainActivity
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BackgroundLocationService() : Service() {
    @SuppressLint("MissingPermission")
    private val TAG = "BackgroundLocation"
    private val handler = Handler()
    private val interval: Long = 1500  // 1.5 segundos
    private val CHANNEL_ID = "BackgroundLocationChannel"

    // Variables de localización
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationManager: LocationManager
    private val locationRepository = LocationRepository()
    var idStudenMain : String = ""
   //val sharedPreferences : SharedPreferences = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Servicio iniciado en segundo plano")
        super.onCreate()

        // Iniciar el servicio en primer plano
        startForegroundService()

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
                    var sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    var idStudentSP = sharedPreferences.getString("idStudent","")
                    //Toast.makeText(this@BackgroundLocationService, "id en preferences: " + idStudentSP, Toast.LENGTH_SHORT).show()
                   // var idSudentSP = sharedPreferences.getString("idStudent","")
                 //   Toast.makeText(this@BackgroundLocationService, "id en preferences: " + idSudentSP, Toast.LENGTH_SHORT).show()
                    if(!idStudentSP.toString().isBlank()){
                        CoroutineScope(Dispatchers.IO).launch {
                            val x = LocationStudentModel(
                                idStudent = idStudentSP.toString(),
                                dateLocation = "",
                                longitudeStudent = location.longitude.toString(),
                                latitudeStudent = location.latitude.toString()
                            )
                            locationRepository.sendLocation(x)
                        }
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

    private fun startForegroundService() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Background Location Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tracking location")
            .setContentText("Your location is being tracked in the background")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }
}
