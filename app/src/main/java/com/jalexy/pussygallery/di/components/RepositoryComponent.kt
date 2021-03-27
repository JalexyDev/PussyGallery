package com.jalexy.pussygallery.di.components

import com.jalexy.pussygallery.di.modules.PussyRepositoryModule
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PussyRepositoryModule::class])
interface RepositoryComponent {
    fun inject(favoritePresenter: PussyFavoritePresenter)
    fun inject(searchPresenter: PussySearchPresenter)
}