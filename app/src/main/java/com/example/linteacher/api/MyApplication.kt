package com.example.linteacher.api

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var sContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        sContext =applicationContext
    }
}
