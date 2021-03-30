package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.model.entities.MyPussy.Companion.FALSE
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView
import io.reactivex.disposables.Disposable
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
            .subscribe(
                { list ->
                    val portion = list.map {
                        MyPussy(it.id, it.subId ?: "", it.url, FALSE)
                    }

                    myPussyItemsCache.addAll(portion)

                    fragmentView.addPussies(portion as ArrayList<MyPussy>)
                    fragmentView.finishLoading()
                    isFree = true
                },
                { throwable ->
                    Log.e("get request ", throwable?.message ?: "PUK")
                    fragmentView?.showError()
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

    fun setFavoriteState(holder: PussyHolderView, pussy: MyPussy) {
        val disposable: Disposable =
            repository.getFavoriteByIdOrPussyId(pussyId = pussy.pussyId)
                .subscribe(
                    {
                        if (it != MyPussy.EMPTY_PUSSY) {
                            holder.setPussyFavorite(it != null)
                            pussy.setInFavorite(it != null)
                        } else {
                            holder.setPussyFavorite(false)
                        }
                    },
                    {
                        holder?.setPussyFavorite(false)
                    })

        unsubscribeOnDestroy(disposable)
    }

    fun favoriteClicked(holder: PussyHolderView, pussy: MyPussy) {
        val isFavorite = pussy.isInFavorite()

        val completable = if (isFavorite) {
            repository.deleteFavorite(pussy)
        } else {
            repository.addToFavorite(pussy)
        }

        val disposable: Disposable =
            completable
                .doOnComplete {
                    holder.setPussyFavorite(!isFavorite)
                    pussy.setInFavorite(!isFavorite)
                }
                .doOnError { holder?.setPussyFavorite(isFavorite) }
                .subscribe()

        unsubscribeOnDestroy(disposable)
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