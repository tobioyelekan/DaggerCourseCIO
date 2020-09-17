package com.example.daggercourse.testing.app.githubapi

import android.content.Context
import com.example.daggercourse.appcomponent.ApplicationComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestGithubApiModule::class])
interface TestApplicationComponent : ApplicationComponent {

    fun gitHubAPi(): FakeGithubApi

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestApplicationComponent
    }
}