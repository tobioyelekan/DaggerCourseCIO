package com.example.daggercourse.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggercourse.di.scope.ScreenScope
import com.example.daggercourse.home.list.RepoItem
import com.example.daggercourse.navigation.DetailsScreen
import com.example.daggercourse.navigation.ScreenNavigator
import com.example.daggercourse.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class HomeViewModel @Inject constructor(
    repository: AppRepository,
    private val screenNavigator: ScreenNavigator
) : ViewModel() {
    private val _viewState = MutableLiveData<HomeViewState>(HomeViewStateLoading)
    val viewStateUpdates: LiveData<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            val topRepos = repository.getTopRepos()
            _viewState.value = HomeViewStateLoaded(
                repos = topRepos.map {
                    RepoItem(
                        ownerName = it.owner.login,
                        name = it.name,
                        description = it.description ?: "",
                        starsCount = it.stargazersCount,
                        forkCount = it.forksCount
                    )
                }
            )
        }
    }

    fun onRepoSelected(repoOwner: String, repoName: String) {
        screenNavigator.goToScreen(DetailsScreen(repoOwner, repoName))
    }
}