package cst.unitbvfmi2026

import android.app.Application

class ApplicationController : Application() { // reprezentant al procesului aplicatiei noastre
    companion object {
        lateinit var instance: ApplicationController
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}