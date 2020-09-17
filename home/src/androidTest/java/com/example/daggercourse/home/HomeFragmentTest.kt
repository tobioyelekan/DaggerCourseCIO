package com.example.daggercourse.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.daggercourse.githubapi.model.RepoApiModel
import com.example.daggercourse.githubapi.model.UserApiModel
import com.example.daggercourse.testing.app.githubapi.TestApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Before
    fun setup(){
        val githubApi = TestApplication.component.gitHubAPi()
        githubApi.topRepos = listOf(
            RepoApiModel(
                id = 1L,
                name = "Home Fragment",
                description = "Mock Repo Description",
                owner = UserApiModel(id = 1L, login = "Repo Guy"),
                stargazersCount = 1,
                forksCount = 1,
                contributorsUrl = "",
                createdDate = "1/1/2020",
                updatedDate = "1/1/2020"
            )
        )
    }

    @Test
    fun reposDisplay(){
        launchFragmentInContainer<HomeFragment>()
        onView(withId(R.id.repo_name)).check(matches(withText("Home Fragment")))
    }

}