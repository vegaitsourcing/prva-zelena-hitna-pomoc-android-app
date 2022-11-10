package org.zelenikljuc.prvazelenahitnapomoc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GreenApplication : Application() {

    companion object {
        lateinit var instance: GreenApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}