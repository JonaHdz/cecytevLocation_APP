package com.example.cecytevlocationapp.utility

import android.content.Context
import androidx.appcompat.app.AlertDialog

class AlertMessage {

     fun showAlertDialog(title: String, message: String,context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("ACEPTAR") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}