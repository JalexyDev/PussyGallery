package com.jalexy.pussygallery.mvp.presenter


import android.util.Log
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.view.PussyFavoriteFragmentView
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class PussyFavoritePresenter(override val fragmentView: PussyFavoriteFragmentView) : BasePresenter(fragmentView) {

    @Inject
    override lateinit var repository: PussyRepository

    init {
        getRepositoryComponent().inject(this)
        myPussyItemsCache = ArrayList()
        registerOnUpdates()
    }

    override fun getPussies() {
        isFree = false

        val disposable: Disposable = repository.getFavorites()
            .subscribe(
                { list ->
                    myPussyItemsCache.addAll(list)

                    fragmentView.addPussies(list as ArrayList<MyPussy>)
                    fragmentView.finishLoading()
                    isFree = true
                },
                { throwable ->
                    Log.e("get request ", throwable?.message ?: "PUK")
                    fragmentView?.showError()
                    isFree = true
                })

        unsubscribeOnDestroy(disposable)
    }

    override fun removed(pussy: MyPussy) {
        fragmentView.removePussy(pussy)
    }

    override fun added(pussy: MyPussy) {
        fragmentView.addPussy(pussy)
    }

    override fun registerOnUpdates() {
        repository.addDbChangeListener(this)
    }

    override fun retryLoad() {
        //todo повторить последний запрос
    }

}