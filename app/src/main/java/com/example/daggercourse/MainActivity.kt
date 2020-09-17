package com.example.daggercourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.daggercourse.details.RepoDetailsFragment
import com.example.daggercourse.home.HomeFragment
import com.example.daggercourse.navigation.DetailsScreen
import com.example.daggercourse.navigation.NAVIGATION_DEPS_SERVICE
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var screenNavigator: ActivityDrivenScreenNavigator
    private lateinit var component: MainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = injectAndGetComponent()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.screen_container, HomeFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        screenNavigator.handleGoToScreen = { screen ->
            when (screen) {
                is DetailsScreen -> supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.screen_container,
                        RepoDetailsFragment.newInstance(screen.repoOwner, screen.repoName)
                    )
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun onStop() {
        screenNavigator.handleGoToScreen = null
        super.onStop()
    }

    override fun getSystemService(name: String): Any? {
        if (name === NAVIGATION_DEPS_SERVICE) {
            return component
        }

        return super.getSystemService(name)
    }
}