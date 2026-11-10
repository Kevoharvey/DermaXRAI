package com.codenytra.amme.dermaxrai.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun isCameraPermissionGranted(context: Context): Boolean {
    val permissionStatus = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    )
    return permissionStatus == PackageManager.PERMISSION_GRANTED
}