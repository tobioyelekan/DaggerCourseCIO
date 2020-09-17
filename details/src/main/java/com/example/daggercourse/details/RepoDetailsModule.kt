package com.example.daggercourse.details

import androidx.lifecycle.ViewModel
import com.example.daggercourse.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RepoDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailsViewModel::class)
    fun bindViewModel(repoDetailsViewModel: RepoDetailsViewModel): ViewModel
}