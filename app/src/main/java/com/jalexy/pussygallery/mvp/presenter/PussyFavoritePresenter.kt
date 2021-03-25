package com.jalexy.pussygallery.mvp.presenter

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.PussyApiManager
import com.jalexy.pussygallery.mvp.view.FavoriteFragmentView
import com.jalexy.pussygallery.mvp.view.PussyFavoriteFragment
import moxy.InjectViewState
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@InjectViewState
class PussyFavoritePresenter : BasePresenter<FavoriteFragmentView>() {

    @Inject
    lateinit var apiManager: PussyApiManager

    init {
        PussyApplication.appComponent.inject(this)
    }

    fun getImages() {
        //todo получить список избранных кисок из локальной бд
    }

}