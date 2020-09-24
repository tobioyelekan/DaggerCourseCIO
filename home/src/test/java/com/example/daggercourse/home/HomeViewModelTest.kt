package com.example.daggercourse.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.daggercourse.githubapi.model.RepoApiModel
import com.example.daggercourse.githubapi.model.UserApiModel
import com.example.daggercourse.home.list.RepoItem
import com.example.daggercourse.navigation.DetailsScreen
import com.example.daggercourse.repository.AppRepository
import com.example.daggercourse.testing.app.githubapi.FakeGithubApi
import com.example.daggercourse.testing.app.navigation.FakeScreenNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

val fakeRepoApiModel = RepoApiModel(
    id = 1L,
    name = "Mock Repo",
    description = "Mock Repo Description",
    owner = UserApiModel(id = 1L, login = "Repo Guy"),
    stargazersCount = 1,
    forksCount = 1,
    contributorsUrl = "",
    createdDate = "1/1/2020",
    updatedDate = "1/1/2020"
)

class HomeViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewStateValues: MutableList<HomeViewState>
    private lateinit var screenNavigator: FakeScreenNavigator

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val appRepository =
            AppRepository(FakeGithubApi().apply { topRepos = listOf(fakeRepoApiModel) })
        viewStateValues = mutableListOf()

        viewModel = HomeViewModel(appRepository, screenNavigator)
        viewModel.viewStateUpdates.observeForever {
            viewStateValues.add(it)
        }
    }

    @Test
    fun `loaded state contains repo modules`() {
        assertEquals(viewStateValues.size, 1)
        val expectedState = HomeViewStateLoaded(
            repos = listOf(
                RepoItem(
                    ownerName = fakeRepoApiModel.owner.login,
                    name = fakeRepoApiModel.name,
                    description = fakeRepoApiModel.description ?: "",
                    starsCount = fakeRepoApiModel.stargazersCount,
                    forkCount = fakeRepoApiModel.forksCount
                )
            )
        )

        assertEquals(viewStateValues[0], expectedState)
    }

    @Test
    fun `repoSelected calls gotoScreen`() {
        viewModel.onRepoSelected(fakeRepoApiModel.owner.login, fakeRepoApiModel.name)
        val expectedScreen = DetailsScreen(fakeRepoApiModel.owner.login, fakeRepoApiModel.name)

        assertEquals(screenNavigator.openedScreens.size, 1)
        assertEquals(screenNavigator.openedScreens[0], expectedScreen)

    }
}