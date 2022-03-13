package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import com.jalexy.pussygallery.data.database.DbChangeListener
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View: PussyListFragmentView>(
    private val repository: PussyRepository
) : DbChangeListener{

    protected abstract var fragmentView: View
    protected lateinit var myPussyItemsCache: ArrayList<MyPussy>
    protected var isFree = true

    private val disposables = CompositeDisposable()

    abstract fun retryLoad()

    protected abstract fun getPussies()

    fun setView(view: View) {
        fragmentView = view
    }

    fun fragmentOpened() {
        fragmentView.loadFragment()
    }

    fun fragmentStarted(isLoaded: Boolean) {
        if (myPussyItemsCache.isEmpty()) {
            getPussies()
        } else{
            if (isLoaded.not()) {
                fragmentView.addPussies(myPussyItemsCache)
            }
            fragmentView.finishLoading()
        }
    }

    fun setFavoriteState(holder: PussyHolderView, pussy: MyPussy) {
        val disposable: Disposable =
            repository.getFavoriteByIdOrPussyId(pussy.pussyId)
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
                    pussy.setInFavorite(!isFavorite)

                    if (isFavorite) {
                        repository.pussyRemovedFromFavorite(pussy)
                    } else {
                        repository.pussyAddedToFavorite(pussy)
                    }
                }
                .doOnError {
                    Log.e("Base Presenter", it.message ?: "presenter exception")
                    holder?.setPussyFavorite(isFavorite)
                }
                .subscribe()

        unsubscribeOnDestroy(disposable)
    }

    open fun refreshFragment() {
        fragmentView.loadFragment()
        myPussyItemsCache = ArrayList()
        fragmentView.refresh()
    }

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun destroy() {
        disposables.clear()
    }
}