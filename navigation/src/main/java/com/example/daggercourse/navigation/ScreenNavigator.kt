package com.example.daggercourse.navigation

import android.annotation.SuppressLint
import android.app.Activity
import java.lang.NullPointerException

sealed class Screen
data class DetailsScreen(val repoOwner: String, val repoName: String) : Screen()

interface ScreenNavigator {
    fun gotoScreen(screen: Screen)
}

interface NavigationDeps {
    fun screenNavigator(): ScreenNavigator
}

const val NAVIGATION_DEPS_SERVICE = "com.example.daggercourse.navigationdeps"

@SuppressLint("WrongConstant")
fun Activity.navigationDeps(): NavigationDeps {
    val navigationDeps = getSystemService(NAVIGATION_DEPS_SERVICE) as? NavigationDeps
        ?: applicationContext.getSystemService(NAVIGATION_DEPS_SERVICE) as? NavigationDeps

    return navigationDeps ?: throw NullPointerException("Activity must override getSystemService")
}