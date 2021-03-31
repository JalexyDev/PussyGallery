package com.jalexy.pussygallery.di.modules

import android.app.Application
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideSearchPresenter() = PussySearchPresenter()

    @Provides
    @Singleton
    fun provideFavoritePresenter() = PussyFavoritePresenter()
}