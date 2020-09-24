package com.example.daggercourse.testing.app.navigation

import com.example.daggercourse.navigation.Screen
import com.example.daggercourse.navigation.ScreenNavigator
import javax.inject.Singleton

@Singleton
class FakeScreenNavigator : ScreenNavigator {

    val openedScreens = mutableListOf<Screen>()

    override fun gotoScreen(screen: Screen) {
        openedScreens.add(screen)
    }
}