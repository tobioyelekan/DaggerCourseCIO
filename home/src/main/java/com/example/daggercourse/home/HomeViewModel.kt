package com.example.daggercourse.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggercourse.di.scope.ScreenScope
import com.example.daggercourse.home.list.RepoItem
import com.example.daggercourse.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class HomeViewModel @Inject constructor(
    repository: AppRepository
) : ViewModel() {
    private val _viewState = MutableLiveData<HomeViewState>(HomeViewStateLoading)
    val viewStateUpdates: LiveData<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            val topRepos = repository.getTopRepos()
            _viewState.value = HomeViewStateLoaded(
                repos = topRepos.map {
                    RepoItem(
                        it.name,
                        it.description ?: "",
                        it.stargazersCount,
                        it.forksCount
                    )
                }
            )
        }
    }
}