package com.example.daggercourse.home

import com.example.daggercourse.appdeps.ApplicationDeps
import com.example.daggercourse.appdeps.applicationDeps
import com.example.daggercourse.di.component.getComponent
import com.example.daggercourse.di.scope.ScreenScope
import com.example.daggercourse.navigation.NavigationDeps
import com.example.daggercourse.navigation.navigationDeps
import dagger.Component

@ScreenScope
@Component(
    dependencies = [ApplicationDeps::class, NavigationDeps::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(applicationDeps: ApplicationDeps, navigationDeps: NavigationDeps): HomeComponent
    }
}

fun HomeFragment.inject() {
    getComponent {
        DaggerHomeComponent.factory()
            .create(requireContext().applicationDeps(), requireActivity().navigationDeps())
    }.inject(this)
}