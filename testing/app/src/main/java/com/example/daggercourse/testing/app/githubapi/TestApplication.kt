package com.example.daggercourse.testing.app.githubapi

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.example.daggercourse.appdeps.ApplicationDeps
import com.example.daggercourse.appdeps.HasApplicationDeps
import com.example.daggercourse.navigation.NAVIGATION_DEPS_SERVICE

class TestApplication : Application(), HasApplicationDeps {

    companion object {
        val component: TestApplicationComponent
            get() = (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication).component
    }

    private lateinit var component: TestApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerTestApplicationComponent.factory().create(this)
    }

    override fun getApplicationDeps(): ApplicationDeps {
        return component
    }

    override fun getSystemService(name: String): Any? {
        if (name === NAVIGATION_DEPS_SERVICE) {
            return component
        }
        return super.getSystemService(name)
    }

}