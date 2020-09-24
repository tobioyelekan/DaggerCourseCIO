package com.example.daggercourse.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggercourse.di.scope.ScreenScope
import com.example.daggercourse.home.list.NumberItem
import com.example.daggercourse.home.list.RepoItem
import com.example.daggercourse.navigation.DetailsScreen
import com.example.daggercourse.navigation.ScreenNavigator
import com.example.daggercourse.poweradapter.RecyclerItem
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
            val listItems = mutableListOf<RecyclerItem>()
            topRepos.forEachIndexed { index, repoApiModel ->
                listItems.add(NumberItem(number = index + 1))
                listItems.add(
                    RepoItem(
                        ownerName = repoApiModel.owner.login,
                        name = repoApiModel.name,
                        description = repoApiModel.description ?: "",
                        starsCount = repoApiModel.stargazersCount,
                        forkCount = repoApiModel.forksCount
                    )
                )
            }
            _viewState.value = HomeViewStateLoaded(listItems)
        }
    }

    fun onRepoSelected(repoOwner: String, repoName: String) {
        screenNavigator.goToScreen(DetailsScreen(repoOwner, repoName))
    }
}