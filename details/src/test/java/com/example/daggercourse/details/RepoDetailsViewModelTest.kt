package com.example.daggercourse.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.daggercourse.details.list.ContributorItem
import com.example.daggercourse.githubapi.model.ContributorApiModel
import com.example.daggercourse.githubapi.model.RepoApiModel
import com.example.daggercourse.githubapi.model.UserApiModel
import com.example.daggercourse.repository.AppRepository
import com.example.daggercourse.testing.app.githubapi.FakeGithubApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepoDetailsViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RepoDetailsViewModel
    private lateinit var repoInfoViewStateValues: MutableList<RepoInfoViewState>
    private lateinit var repoContributorsViewStateValues: MutableList<RepoContributorsViewState>

    private val fakeRepoApiModel = RepoApiModel(
        id = 1L,
        name = "Mock Repo",
        description = "Mock Repo Description",
        owner = UserApiModel(id = 1L, login = "dagger"),
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/1/2020",
        updatedDate = "1/1/2020"
    )

    private val fakeContributorsList = listOf(
        ContributorApiModel(id = 1L, login = "contributor", avatarUrl = "avatar.png")
    )

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        repoInfoViewStateValues = mutableListOf()
        repoContributorsViewStateValues = mutableListOf()

        val appRepository = AppRepository(FakeGithubApi().apply {
            singleRepoResult = fakeRepoApiModel
            contributorsResult = fakeContributorsList
        })

        viewModel = RepoDetailsViewModel("repo_owner", "repo_name", appRepository)
        viewModel.repoInfoUpdates.observeForever { repoInfoViewStateValues.add(it) }
        viewModel.contributorsUpdates.observeForever { repoContributorsViewStateValues.add(it) }
    }

    @Test
    fun `repo info loaded`() {
        assertThat(repoInfoViewStateValues.size).isEqualTo(1)
        val expectedState = RepoInfoViewStateLoaded(
            repoName = fakeRepoApiModel.name,
            repoDescription = fakeRepoApiModel.description ?: "",
            createdDate = fakeRepoApiModel.createdDate,
            updatedDate = fakeRepoApiModel.updatedDate
        )

        assertThat(repoInfoViewStateValues[0]).isEqualTo(expectedState)
    }

    @Test
    fun `repo contributors loaded`() {
        assertThat(repoContributorsViewStateValues.size).isEqualTo(1)
        val expectedState = RepoContributorsViewStateLoaded(
            contributors = fakeContributorsList.map { apiModel ->
                ContributorItem(
                    id = apiModel.id,
                    name = apiModel.login,
                    avatarUrl = apiModel.avatarUrl
                )
            }
        )

        assertThat(repoContributorsViewStateValues[0]).isEqualTo(expectedState)
    }
}