package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.model.entities.MyPussy.Companion.FALSE
import com.jalexy.pussygallery.mvp.view.PussySearchFragmentView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class PussySearchPresenter(override val fragmentView: PussySearchFragmentView) : BasePresenter(fragmentView) {

    @Inject
    override lateinit var repository: PussyRepository

    // для задания полная форма бесполезна, но если надо, то вот она
    // private var imageItemsCash: ArrayList<Image>

    private var page = 0

    init {
        getRepositoryComponent().inject(this)
        myPussyItemsCache = ArrayList()
        registerOnUpdates()
    }

    override fun getPussies() {
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
                    isFree = true
                })

        unsubscribeOnDestroy(disposable)
    }

    override fun retryLoad() {
        //todo повторить последний запрос
    }

    // подгрузка если долистал до конца
    fun scrolledToEnd() {
        if (isFree) {
            fragmentView.loadItems()
            page++
            getPussies()
        }
    }

    override fun refreshFragment() {
        page = 0
        super.refreshFragment()
    }

    override fun removed(pussy: MyPussy) {
        fragmentView.updatePussy(pussy)
    }

    override fun added(pussy: MyPussy) {
        fragmentView.updatePussy(pussy)
    }

    override fun registerOnUpdates() {
        repository.addDbChangeListener(this)
    }
}