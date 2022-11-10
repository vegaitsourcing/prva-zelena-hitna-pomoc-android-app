package org.zelenikljuc.prvazelenahitnapomoc.util

import android.app.Activity
import android.content.IntentSender
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

fun Fragment.displayLocationSettingsRequest(requestCode: Int, activity: Activity) {
    val locationRequest = LocationRequest.create()
    locationRequest.apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 10000
        fastestInterval = 10000 / 2.toLong()
    }

    LocationServices.getSettingsClient(activity).checkLocationSettings(
        LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()
    ).addOnCompleteListener { task ->
        try {
            task.getResult(ApiException::class.java)
        } catch (exception: ApiException) {
            when (exception.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    try {
                        val resolvable = exception as (ResolvableApiException)

                        startIntentSenderForResult(
                            resolvable.resolution.intentSender,
                            requestCode,
                            null,
                            0,
                            0,
                            0,
                            null
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}