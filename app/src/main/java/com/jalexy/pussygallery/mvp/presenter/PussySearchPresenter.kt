package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.PussyApiManager
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.Image
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PussySearchPresenter(val fragmentView: PussyListFragmentView) : BasePresenter() {

    @Inject
    lateinit var repository: PussyRepository

    init {
        getRepositoryComponent().inject(this)
    }

    private fun getImages() {
        val disposable: Disposable = repository.getImages()
            .unsubscribeOn(Schedulers.io())
            .subscribe({ list ->
                list?.let {
                    for (i in it.indices) {
                        Log.d("IdShmaidy#$i ->", it[i].id ?: "PUK")
                    }
                }
            }, { throwable -> Log.e("get request ", throwable?.message ?: "PUK") })

        unsubscribeOnDestroy(disposable)
    }

    fun fragmentOpened() {

        fragmentView.loadFragment()
    }

    fun fragmentStarted() {
        val disposable: Disposable = repository.getImages()
            .unsubscribeOn(Schedulers.io())
            .subscribe({ list ->

                fragmentView.addImages(list)
                fragmentView.finishLoading()

                list?.let {
                    for (i in it.indices) {
                        Log.d("IdShmaidy#$i ->", it[i].id ?: "PUK")
                    }
                }
            }, { throwable -> Log.e("get request ", throwable?.message ?: "PUK") })

        unsubscribeOnDestroy(disposable)
    }

    fun scrolledToEnd(position: Int) {
        //todo когда доскролили до конца списка фоток и нужна подгрузка
    }

    fun itemClicked(image: Image) {
        //todo когда нажали на итем для просмотра фотки
    }

    fun favoriteClicked(image: Image) {
        //todo нажали на кнопку добавить/удалить из избранного
    }

    fun refresh() {
        //todo обнуляем фрагмент, делаем запрос как в Started
    }
}