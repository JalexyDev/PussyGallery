package com.jalexy.pussygallery.mvp.view

import com.jalexy.pussygallery.mvp.model.entities.MyPussy

interface PussySearchFragmentView : PussyListFragmentView {

    // обновляем итем в адаптере
    fun updatePussy(pussy: MyPussy)
}