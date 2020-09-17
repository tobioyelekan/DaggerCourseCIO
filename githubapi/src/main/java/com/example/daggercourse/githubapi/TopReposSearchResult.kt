package com.example.daggercourse.githubapi

import com.example.daggercourse.githubapi.model.RepoApiModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopReposSearchResult(val items: List<RepoApiModel>)