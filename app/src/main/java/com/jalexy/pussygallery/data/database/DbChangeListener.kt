package com.jalexy.pussygallery.data.database

import com.jalexy.pussygallery.mvp.model.entities.MyPussy

interface DbChangeListener {

    fun removed(pussy: MyPussy)

    fun added(pussy: MyPussy)

    fun registerOnUpdates()
}