package com.jalexy.pussygallery.mvp.view

import com.jalexy.pussygallery.mvp.model.entities.MyPussy


interface PussyListFragmentView {

    // показывает лоадер на отдельном слайде ViewFlipper
    fun loadFragment()

    // показывает сообщение об ошибке на отдельном слайде ViewFlipper
    fun showError()

    // показывает лоадер в конце списка, при подгрузке
    fun loadItems()

    // отрубает всю анимацию загрузки и показывает список итемов
    fun finishLoading()

    // добавляет список Image в адаптер RecyclerView
    fun addPussies(pussies: ArrayList<MyPussy>)

    // добавляет Image в адаптер
    fun addPussy(pussy: MyPussy)

    // возвращает все в первоначальный вид. Вызывается рефрешером
    fun refresh()
}