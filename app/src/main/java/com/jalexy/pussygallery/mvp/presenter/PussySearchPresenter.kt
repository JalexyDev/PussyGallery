package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PussySearchPresenter(val fragmentView: PussyListFragmentView) : BasePresenter() {

    @Inject
    lateinit var repository: PussyRepository

    // для задания полная форма бесполезна, но если надо, то вот она
//    private var imageItemsCash: ArrayList<Image>

    private var myPussyItemsCache: ArrayList<MyPussy>
    private var page = 0

    init {
        getRepositoryComponent().inject(this)
        myPussyItemsCache = ArrayList()
    }

    private fun getImages(page: Int = 0) {
        isFree = false

        val disposable: Disposable = repository.getImages(page = page)
            .unsubscribeOn(Schedulers.io())
            .subscribe({ list ->

                val portion = list.map {
                    //todo узнать, находится ли эта киска в избранном. И установить значение isFavorite
                    MyPussy(it.id, it.subId ?: "", it.url, 0)
                }

                myPussyItemsCache.addAll(portion)

                fragmentView.addPussies(portion as ArrayList<MyPussy>)
                fragmentView.finishLoading()
                isFree = true

            },
                { throwable ->
                    Log.e("get request ", throwable?.message ?: "PUK")
                    fragmentView.showError()
                })

        unsubscribeOnDestroy(disposable)
    }

    fun fragmentOpened() {
        fragmentView.loadFragment()
    }

    fun fragmentStarted() {
        if (myPussyItemsCache.isEmpty()) {
            getImages()
        } else {
            fragmentView.addPussies(myPussyItemsCache)
        }
    }

    fun scrolledToEnd() {
        if (isFree) {
            fragmentView.loadItems()
            getImages(++page)
        }
    }

    fun favoriteClicked(holder: PussyHolderView, pussy: MyPussy) {
        //todo если все норм, добавить избранную фотку в БД

//        val disposable: Disposable = repository.addToFavorite(pussy.pussyId)
//            .unsubscribeOn(Schedulers.io())
//            .subscribe({ okResponse ->
//                Log.d("Test", okResponse.message)
//
//                holder.setPussyFavorite(!pussy.isFavorite)
//            },
//                { throwable ->
//                    Log.e("Test", throwable?.message ?: "PUK")
//                })
//
//        unsubscribeOnDestroy(disposable)
    }

    fun retryLoad() {
        //todo повторить последний запрос на подгрузку
    }

    fun refreshFragment() {
        fragmentView.loadFragment()

        myPussyItemsCache = ArrayList()
        page = 0

        fragmentView.refresh()
    }
}