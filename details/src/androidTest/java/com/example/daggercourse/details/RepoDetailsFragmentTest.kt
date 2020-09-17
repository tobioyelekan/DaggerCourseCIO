package com.example.daggercourse.details

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.daggercourse.githubapi.model.ContributorApiModel
import com.example.daggercourse.githubapi.model.RepoApiModel
import com.example.daggercourse.githubapi.model.UserApiModel
import com.example.daggercourse.testing.app.githubapi.TestApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailsFragmentTest {

    private val fakeRepoApiModel = RepoApiModel(
        id = 1L,
        name = "my name is tobi",
        description = "Android",
        owner = UserApiModel(1L, "LoginOwner"),
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "url",
        createdDate = "today",
        updatedDate = "yesterday"
    )

    private val fakeContributor =
        ContributorApiModel(id = 1L, login = "contributor", avatarUrl = "avatar.png")

    @Before
    fun setup() {
        val githubAPi = TestApplication.component.gitHubAPi()
        githubAPi.singleRepoResult = fakeRepoApiModel
        githubAPi.contributorsResult = listOf(fakeContributor)
    }

    @Test
    fun loadedStateDisplayExpectedData() {
        launchFragmentInContainer<RepoDetailsFragment>(
            fragmentArgs = Bundle().apply {
                putString("repo_owner", "owner")
                putString("repo_name", "name")
            }
        )

        onView(withId(R.id.details_loading_indicator))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.contributor_loading_indicator))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.repo_name)).check(matches(withText(fakeRepoApiModel.name)))
        onView(withId(R.id.repo_description)).check(matches(withText(fakeRepoApiModel.description)))

        onView(withId(R.id.contributorName)).check(matches(withText(fakeContributor.login)))
    }


}