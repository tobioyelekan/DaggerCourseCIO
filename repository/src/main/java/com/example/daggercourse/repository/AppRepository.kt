package com.example.daggercourse.repository

import com.example.daggercourse.githubapi.GitHubApi
import com.example.daggercourse.githubapi.model.ContributorApiModel
import com.example.daggercourse.githubapi.model.RepoApiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val githubApi: GitHubApi) {

    private val cachedRepos = mutableListOf<RepoApiModel>()

    suspend fun getTopRepos(): List<RepoApiModel> {
        if (cachedRepos.isEmpty()) {
            cachedRepos.addAll(githubApi.getTopRepositories().items)
        }

        return cachedRepos
    }

    suspend fun getRepo(repoOwner: String, repoName: String): RepoApiModel {
        return getRepoFromCache(repoOwner, repoName) ?: githubApi.getRepo(repoOwner, repoName)
    }

    suspend fun getContributors(repoOwner: String, repoName: String): List<ContributorApiModel> {
        return githubApi.getContributor(repoOwner, repoName)
    }

    private fun getRepoFromCache(repoOwner: String, repoName: String): RepoApiModel? {
        return cachedRepos.firstOrNull { repo ->
            repo.owner.login == repoOwner && repo.name == repoName
        }
    }
}