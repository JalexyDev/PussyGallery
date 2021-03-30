package com.jalexy.pussygallery.mvp.view

import com.jalexy.pussygallery.mvp.model.entities.MyPussy

interface PussyFavoriteFragmentView : PussyListFragmentView{

    // добавляет киску в адаптер
    fun addPussy(pussy: MyPussy)

    // удаляет киску из адаптера
    fun removePussy(pussy: MyPussy)
}