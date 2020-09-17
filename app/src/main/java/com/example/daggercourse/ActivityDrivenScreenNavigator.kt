package com.example.daggercourse

import com.example.daggercourse.navigation.Screen
import com.example.daggercourse.navigation.ScreenNavigator
import javax.inject.Inject

class ActivityDrivenScreenNavigator @Inject constructor() : ScreenNavigator {

    var handleGoToScreen: ((Screen) -> Unit)? = null

    override fun gotoScreen(screen: Screen) {
        handleGoToScreen?.invoke(screen)
    }

}