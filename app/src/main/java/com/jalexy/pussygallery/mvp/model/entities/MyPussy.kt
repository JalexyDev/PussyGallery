package com.jalexy.pussygallery.mvp.model.entities

import com.jalexy.pussygallery.mvp.view.ui.adapters.Item
import com.jalexy.pussygallery.mvp.view.ui.adapters.ItemType

// класс для списков кисок и хранения избранного в БД
data class MyPussy(
    var id: Int,
    var pussyId: String,
    var subId: String,
    var url: String,
    private var isFavorite: Int
) : Item {

    constructor(pussyId: String, subId: String, url: String, isFavorite: Int) :
            this(-1, pussyId, subId, url, isFavorite)

    companion object {
        const val TRUE = 1
    }


    override fun getType(): ItemType {
        return ItemType.ITEM
    }

    fun isInFavorite() : Boolean{
        return isFavorite == TRUE
    }
}