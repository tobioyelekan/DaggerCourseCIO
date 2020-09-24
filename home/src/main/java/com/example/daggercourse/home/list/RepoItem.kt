package com.example.daggercourse.home.list

data class RepoItem(
    val ownerName: String,
    val name: String,
    val description: String,
    val starsCount: Int,
    val forkCount: Int
)