package com.jalexy.pussygallery.di.modules

import android.app.Application
import com.jalexy.pussygallery.database.DatabaseHandler
import com.jalexy.pussygallery.mvp.model.PussyFavoriteDbManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PussyDbModule {

    @Provides
    @Singleton
    fun provideDbHandler(application: Application) =
        DatabaseHandler(application.applicationContext)

    @Provides
    @Singleton
    fun providePussyDbManager(handler: DatabaseHandler) =
        PussyFavoriteDbManager(handler)
}