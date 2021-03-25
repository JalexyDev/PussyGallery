package com.jalexy.pussygallery.di.components

import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import com.jalexy.pussygallery.mvp.view.MainActivity
import com.jalexy.pussygallery.mvp.view.PussyFavoriteFragment
import com.jalexy.pussygallery.mvp.view.PussySearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PussyApiModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(favoritePresenter: PussyFavoritePresenter)
    fun inject(searchPresenter: PussySearchPresenter)
}