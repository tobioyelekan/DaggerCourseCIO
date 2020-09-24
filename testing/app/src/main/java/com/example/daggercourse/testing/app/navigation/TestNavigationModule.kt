package com.example.daggercourse.testing.app.navigation

import com.example.daggercourse.navigation.ScreenNavigator
import dagger.Binds
import dagger.Module

@Module
interface TestNavigationModule {
    @Binds
    fun bindsScreenNavigator(fakeScreenNavigator: FakeScreenNavigator): ScreenNavigator
}