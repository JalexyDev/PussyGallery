package com.jalexy.pussygallery.mvp.presenter

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.PussyApiManager
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView
import javax.inject.Inject


class PussyFavoritePresenter(val fragmentView: PussyListFragmentView) : BasePresenter() {

    @Inject
    lateinit var repository: PussyRepository

    init {
        getRepositoryComponent().inject(this)
    }

    fun getImages() {
        //todo получить список избранных кисок из локальной бд
    }

}