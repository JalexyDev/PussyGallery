package com.jalexy.pussygallery.mvp.model.entities

import com.jalexy.pussygallery.mvp.view.ui.adapters.Item
import com.jalexy.pussygallery.mvp.view.ui.adapters.ItemType

// класс для списков кисок и хранения избранного в БД
data class MyPussy(
    val pussyId: String,
    val subId: String?,
    val url: String,
    var isFavorite: Boolean
    ) : Item {

    override fun getType(): ItemType {
        return ItemType.ITEM
    }
}