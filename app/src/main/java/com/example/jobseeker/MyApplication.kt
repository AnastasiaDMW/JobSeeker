package com.example.jobseeker

import android.app.Application
import com.example.jobseeker.di.AppComponent
import com.example.jobseeker.di.DaggerAppComponent

class MyApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}