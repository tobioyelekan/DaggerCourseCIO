package com.example.daggercourse.repository

import com.example.daggercourse.githubapi.model.ContributorApiModel
import com.example.daggercourse.githubapi.model.RepoApiModel
import com.example.daggercourse.githubapi.model.UserApiModel
import com.example.daggercourse.testing.app.githubapi.FakeGithubApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

val fakeRepoData = RepoApiModel(
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

class AppRepositoryTest {

    private lateinit var appRepository: AppRepository

    private val fakeGithubApi = FakeGithubApi().apply { topRepos = listOf(fakeRepoData) }

    @Before
    fun setup() {
        appRepository = AppRepository(fakeGithubApi)
    }

    @Test
    fun `getTopRepos returns result from GitHubAPi`() {
        val topRepos = runBlocking { appRepository.getTopRepos() }
        assertThat(topRepos.size).isEqualTo(1)
        assertThat(topRepos[0]).isEqualTo(fakeRepoData)
    }

    @Test
    fun `getTopRepos returns cached result`() {
        val initialRequest = runBlocking { appRepository.getTopRepos() }

        fakeGithubApi.topRepos = listOf(fakeRepoData, fakeRepoData)
        val secondRequest = runBlocking { appRepository.getTopRepos() }

        assertThat(initialRequest).isEqualTo(secondRequest)
    }

    @Test
    fun `getRepo returns cached value`() {
        runBlocking { appRepository.getTopRepos() }

        fakeGithubApi.singleRepoResult = fakeRepoData.copy(name = "updated name")

        val singleRepFetchResult = runBlocking {
            appRepository.getRepo(
                repoOwner = fakeRepoData.owner.login,
                repoName = fakeRepoData.name
            )
        }

        assertThat(singleRepFetchResult).isEqualTo(fakeRepoData)
    }

    @Test
    fun `getRepo returns API value if not in cache`() {
        runBlocking { appRepository.getTopRepos() }

        val expectedModel = fakeRepoData.copy(name = "Updated Name")
        fakeGithubApi.singleRepoResult = expectedModel

        val singleRepoFetchResult = runBlocking {
            appRepository.getRepo(
                repoOwner = expectedModel.owner.login,
                repoName = expectedModel.name
            )
        }

        assertThat(singleRepoFetchResult).isEqualTo(expectedModel)
    }

    @Test
    fun `getContributors returns API value`() {
        val expectedContributors = listOf(
            ContributorApiModel(
                id = 1L,
                login = "contributor",
                avatarUrl = "avatar.png"
            )
        )

        fakeGithubApi.contributorsResult = expectedContributors

        val contributors = runBlocking {
            appRepository.getContributors(fakeRepoData.owner.login, fakeRepoData.name)
        }

        assertThat(contributors).isEqualTo(expectedContributors)
    }
}
