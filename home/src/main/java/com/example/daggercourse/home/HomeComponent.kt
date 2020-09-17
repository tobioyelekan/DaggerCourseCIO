package com.example.daggercourse.home

import com.example.daggercourse.appdeps.ApplicationDeps
import com.example.daggercourse.appdeps.applicationDeps
import com.example.daggercourse.di.component.getComponent
import com.example.daggercourse.di.scope.ScreenScope
import dagger.Component

@ScreenScope
@Component(dependencies = [ApplicationDeps::class], modules = [HomeModule::class])
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(applicationDeps: ApplicationDeps): HomeComponent
    }
}

fun HomeFragment.inject() {
    getComponent {
        DaggerHomeComponent.factory()
            .create(requireContext().applicationDeps())
    }.inject(this)
}