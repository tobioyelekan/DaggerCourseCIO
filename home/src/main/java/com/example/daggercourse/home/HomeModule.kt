package com.example.daggercourse.home

import androidx.lifecycle.ViewModel
import com.example.daggercourse.di.viewmodel.ViewModelKey
import com.example.daggercourse.home.list.NumberItem
import com.example.daggercourse.home.list.NumberItemRenderer
import com.example.daggercourse.home.list.RepoItem
import com.example.daggercourse.home.list.RepoItemRenderer
import com.example.daggercourse.poweradapter.ItemRenderer
import com.example.daggercourse.poweradapter.RecyclerItem
import com.example.daggercourse.poweradapter.RendererKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @RendererKey(RepoItem::class)
    fun bindRepoItemRenderer(repoItemRenderer: RepoItemRenderer): ItemRenderer<out RecyclerItem>

    @Binds
    @IntoMap
    @RendererKey(NumberItem::class)
    fun bindNumberItem(numberItemRenderer: NumberItemRenderer): ItemRenderer<out RecyclerItem>
}