package com.example.daggercourse.appcomponent

import android.content.Context
import com.example.daggercourse.appdeps.ApplicationDeps
import com.example.daggercourse.githubapi.GithubApiModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GithubApiModule::class])
interface ApplicationComponent : ApplicationDeps {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}