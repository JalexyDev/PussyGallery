package com.jalexy.pussygallery.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jalexy.pussygallery.data.api.ApiFactory
import com.jalexy.pussygallery.mvp.model.PussyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
class PussyApiModule() {

    @Provides
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10L * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    fun provideGson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()

    @Provides
    fun provideOkhttpClient(cache: Cache) = OkHttpClient.Builder()
        .cache(cache)
        .build()

    @Provides
    fun provideApiService(apiFactory: ApiFactory): PussyApi = apiFactory.apiService
}