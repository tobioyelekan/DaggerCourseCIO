package com.example.daggercourse.githubapi

import com.example.daggercourse.githubapi.model.ContributorApiModel
import com.example.daggercourse.githubapi.model.RepoApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("search/repositories?q=language:kotlin&order=desc&sort=stars")
    suspend fun getTopRepositories(): TopReposSearchResult

    @GET("repos/{owner}/{name}")
    suspend fun getRepo(
        @Path("owner") repoOwner: String,
        @Path("name") repoName: String
    ): RepoApiModel

    @GET("repos/{owner}/{name}/contributors")
    suspend fun getContributor(
        @Path("owner") repoOwner: String,
        @Path("name") repoName: String
    ): List<ContributorApiModel>
}