package com.jalexy.pussygallery.mvp

import com.jalexy.pussygallery.mvp.model.Image

interface PussyView {

    fun startLoadPussies()

    fun finishLoadPussies()

    fun addPussy(pussy: Image)

    fun addPussies(pussies: ArrayList<Image>)

    fun updatePussyAt(index: Int, pussy: Image)

    fun removePussyAt(index: Int)
}

interface PussyPresenter {
    //TODO подумать что еще надо делать
    fun getPussy()

    fun getPussies()

    fun updatePussy()
}