package com.jalexy.pussygallery.di.components

import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule
import com.jalexy.pussygallery.di.modules.PussyDbModule
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PussyApiModule::class, PussyDbModule::class])
interface AppComponent {
    fun inject(favoritePresenter: PussyFavoritePresenter)
    fun inject(searchPresenter: PussySearchPresenter)
}