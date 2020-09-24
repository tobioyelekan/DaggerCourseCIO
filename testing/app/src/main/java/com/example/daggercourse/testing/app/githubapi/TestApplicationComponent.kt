package com.example.daggercourse.testing.app.githubapi

import android.content.Context
import com.example.daggercourse.appcomponent.ApplicationComponent
import com.example.daggercourse.navigation.NavigationDeps
import com.example.daggercourse.testing.app.navigation.TestNavigationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestGithubApiModule::class, TestNavigationModule::class])
interface TestApplicationComponent : ApplicationComponent, NavigationDeps {

    fun gitHubAPi(): FakeGithubApi

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestApplicationComponent
    }
}