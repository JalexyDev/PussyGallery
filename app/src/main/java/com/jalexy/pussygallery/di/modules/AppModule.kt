package com.jalexy.pussygallery.di.modules

import android.app.Application
import com.jalexy.pussygallery.mvp.model.PussyApiManager
import com.jalexy.pussygallery.mvp.model.PussyFavoriteDbManager
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [PussyApiModule::class, PussyDbModule::class])
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

    @Provides
    @Singleton
    fun provideRepository(apiManager: PussyApiManager, dbManager: PussyFavoriteDbManager) =
        PussyRepository(apiManager, dbManager)
}