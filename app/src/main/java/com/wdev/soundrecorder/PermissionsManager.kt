package com.wdev.soundrecorder

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsManager() {



    companion object{


        private val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private val MY_PERMISSIONS_RECORD_AUDIO = 2

        fun verifyStoragePermissions(activity: Activity) {
            // Check if we have write permission
            val permission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE,
                )
            }

            if (ContextCompat.checkSelfPermission(activity.applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
               // Toast.makeText(activity.applicationContext, "Permisos de almacenamiento NO aceptados", Toast.LENGTH_LONG).show()
                val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.
                                    permission.READ_EXTERNAL_STORAGE)

                ActivityCompat.requestPermissions(activity, permissions, REQUEST_EXTERNAL_STORAGE)
            }else{
                //Toast.makeText(activity.applicationContext, "Permisos de almacenamiento aceptados", Toast.LENGTH_LONG).show()
            }
        }

        fun verifyAudioPermissions(activity: Activity){

            if (ActivityCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.
                RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            {
                //Toast.makeText(activity.applicationContext, "Permisos de audio NO aceptados", Toast.LENGTH_LONG).show()
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
                ActivityCompat.requestPermissions(activity, permissions,
                    MY_PERMISSIONS_RECORD_AUDIO)
            } else {
               // Toast.makeText(activity.applicationContext, "Permisos de audio aceptados", Toast.LENGTH_LONG).show()
            }
        }
    }
}