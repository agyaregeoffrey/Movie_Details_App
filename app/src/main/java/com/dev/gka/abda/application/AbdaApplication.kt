package com.dev.gka.abda.application

import android.app.Application
import timber.log.Timber

class AbdaApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}