package com.jalexy.pussygallery.di.components

import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.mvp.view.ui.PussyFavoriteFragment
import com.jalexy.pussygallery.mvp.view.ui.PussySearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface FragmentComponent {
    fun inject(searchFragment: PussySearchFragment)

    fun inject(favoriteFragment: PussyFavoriteFragment)
}