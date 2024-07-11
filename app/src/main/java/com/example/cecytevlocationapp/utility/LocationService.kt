package com.example.cecytevlocationapp.utility

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.SystemClock
import androidx.core.app.JobIntentService
import androidx.core.content.ContextCompat

class LocationService () {
    /*
    companion object {
        private const val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, LocationService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {
        // Aquí puedes llamar a getLocation() u otras operaciones relacionadas con la ubicación
        val location = getLocation()
        // Procesa la ubicación obtenida según sea necesario
    }

    override fun onDestroy() {
        super.onDestroy()
        scheduleNextService(this)
    }

    private fun scheduleNextService(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, LocationService::class.java)
        val pendingIntent = PendingIntent.getService(
            context,
            JOB_ID,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Usar FLAG_IMMUTABLE
        )

        val triggerAtMillis = SystemClock.elapsedRealtime() + (15 * 60 * 1000) // 15 minutos en milisegundos
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent)
    }

    private fun getLocation(): Location? {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Verificar permiso ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            var location: Location? = null
            if (isGPSEnabled) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            if (location == null && isNetworkEnabled) {
                location =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            return location
        } else {
            // Si no se tiene el permiso, deberías manejar este caso de acuerdo a la lógica de tu app
            return null
        }
    }*/
}
