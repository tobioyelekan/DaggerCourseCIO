package com.example.daggercourse.home

import com.example.daggercourse.poweradapter.RecyclerItem

sealed class HomeViewState
object HomeViewStateLoading : HomeViewState()
data class HomeViewStateLoaded(val repos: List<RecyclerItem>) : HomeViewState()
data class HomeViewStateError(val message: String) : HomeViewState()
