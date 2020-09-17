package com.example.daggercourse.testing.app.githubapi

import com.example.daggercourse.githubapi.GitHubApi
import com.example.daggercourse.githubapi.TopReposSearchResult
import com.example.daggercourse.githubapi.model.ContributorApiModel
import com.example.daggercourse.githubapi.model.RepoApiModel
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeGithubApi @Inject constructor() : GitHubApi {

    var topRepos = listOf<RepoApiModel>()
    var singleRepoResult: RepoApiModel? = null
    var contributorsResult = listOf<ContributorApiModel>()

    override suspend fun getTopRepositories(): TopReposSearchResult {
        return TopReposSearchResult(topRepos)
    }

    override suspend fun getRepo(repoOwner: String, repoName: String): RepoApiModel {
        return singleRepoResult ?: throw NullPointerException("singleRepoResult was not set")
    }

    override suspend fun getContributor(
        repoOwner: String,
        repoName: String
    ): List<ContributorApiModel> {
        return contributorsResult
    }
}