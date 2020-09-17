package com.example.daggercourse

import com.example.daggercourse.di.component.getComponent
import com.example.daggercourse.di.scope.ActivityScope
import com.example.daggercourse.navigation.NavigationDeps
import com.example.daggercourse.navigation.ScreenNavigator
import dagger.Binds
import dagger.Component
import dagger.Module

@ActivityScope
@Component(modules = [MainActivityComponent::class])
interface MainActivityComponent : NavigationDeps {
    @Component.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun inject(mainActivity: MainActivity)
}

@Module
interface MainActivityModule {
    @Binds
    fun bindScreenNavigator(activityDrivenScreenNavigator: ActivityDrivenScreenNavigator): ScreenNavigator
}

fun MainActivity.injectAndGetComponent(): MainActivityComponent {
    val component = getComponent { DaggerMainActivityComponent.create() }
    component.inject(this)
    return component
}