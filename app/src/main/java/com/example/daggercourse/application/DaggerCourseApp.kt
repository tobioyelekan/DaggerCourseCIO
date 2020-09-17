package com.example.daggercourse.application

import android.app.Application
import com.example.daggercourse.appcomponent.DaggerApplicationComponent
import com.example.daggercourse.appdeps.ApplicationDeps
import com.example.daggercourse.appdeps.HasApplicationDeps

class DaggerCourseApp : Application(), HasApplicationDeps {
    private val appComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getApplicationDeps(): ApplicationDeps {
        return appComponent
    }
}