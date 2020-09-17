package com.example.daggercourse.testing.app.githubapi

import com.example.daggercourse.githubapi.GitHubApi
import dagger.Binds
import dagger.Module

@Module
interface TestGithubApiModule {

    @Binds
    fun bindsGithubApi(fakeGithubApi: FakeGithubApi): GitHubApi

}