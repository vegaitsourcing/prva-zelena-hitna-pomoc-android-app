package com.example.hakaton.util

import android.Manifest
import android.content.Context
import com.vmadalin.easypermissions.EasyPermissions

object LocaionUtil {

    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    const val ZOOM_LEVEL = 15f
    const val DEFAULT_LAT: Double = 43.3209
    const val DEFAULT_LNG: Double = 21.8945

    fun hasLocationPermissions(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

}