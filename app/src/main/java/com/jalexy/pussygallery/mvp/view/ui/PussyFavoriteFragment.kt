package com.jalexy.pussygallery.mvp.view.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.view.PussyFavoriteFragmentView
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.ui.adapters.BaseRecyclerViewAdapter
import com.jalexy.pussygallery.mvp.view.ui.adapters.PussyRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_image_list.view.*
import javax.inject.Inject


class PussyFavoriteFragment : Fragment(), PussyFavoriteFragmentView,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val LOAD_LAYOUT = 0
        private const val CONTENT_LAYOUT = 1
        private const val ERROR_LAYOUT = 2
        private const val EMPTY_LAYOUT = 3

        @JvmStatic
        fun newInstance(): PussyFavoriteFragment {
            return PussyFavoriteFragment()
        }
    }

    @Inject
    protected lateinit var presenter: PussyFavoritePresenter

    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var flipper: ViewFlipper
    private lateinit var pussyListView: RecyclerView
    private lateinit var errorText: TextView
    private lateinit var retryBtn: Button

    private lateinit var adapter: PussyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PussyApplication.fragmentComponent.inject(this)
        presenter.setView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image_list, container, false)

        refresher = root.refresher
        refresher.setOnRefreshListener(this)
        refresher.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary)

        flipper = root.flipper

        pussyListView = root.pussy_list

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val layoutManager = GridLayoutManager(context, 2)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter?.getItemViewType(position)) {
                        BaseRecyclerViewAdapter.LOADER_TYPE, BaseRecyclerViewAdapter.LOAD_ERROR_TYPE -> 2
                        else -> 1
                    }
                }
            }

            pussyListView.layoutManager = layoutManager

        } else {

            pussyListView.layoutManager = LinearLayoutManager(context)
        }

        pussyListView.itemAnimator = DefaultItemAnimator()

        adapter = PussyRecyclerViewAdapter(
            context,
            {presenter.retryLoad()},
            {holder: PussyHolderView, pussy: MyPussy -> presenter.setFavoriteState(holder, pussy)},
            {holder: PussyHolderView, pussy: MyPussy -> presenter.favoriteClicked(holder, pussy)}
        )

        pussyListView.adapter = adapter

        errorText = root.error_text
        retryBtn = root.retry_btn
        retryBtn.setOnClickListener { onRefresh() }

        presenter.fragmentOpened()

        return root
    }

    override fun onStart() {
        super.onStart()
        presenter.fragmentStarted(adapter.itemCount > 0)
    }

    override fun onDestroy() {
        presenter.destroy()
        adapter.clearItems()
        super.onDestroy()
    }

    override fun loadFragment() {
        flipper.displayedChild = LOAD_LAYOUT
    }

    override fun showError() {
        if (refresher.isRefreshing) refresher.isRefreshing = false

        if (adapter.isEmpty()) {
            flipper.displayedChild = ERROR_LAYOUT
        } else {
            adapter.addError()
        }
    }

    override fun loadItems() {
        adapter.addLoader()
    }

    override fun finishLoading() {
        flipper.displayedChild = if (adapter.isEmpty()) {
            EMPTY_LAYOUT
        } else {
            CONTENT_LAYOUT
        }

        adapter.removeLoader()
    }

    override fun addPussies(pussies: ArrayList<MyPussy>) {
        adapter.addItems(pussies)
    }

    override fun addPussy(pussy: MyPussy) {
        adapter.addItem(pussy)

        if (adapter.isEmpty().not()) {
            flipper.displayedChild = CONTENT_LAYOUT
        }
    }

    override fun removePussy(pussy: MyPussy) {
        adapter.removeItem(pussy)

        if (adapter.isEmpty()) {
            flipper.displayedChild = EMPTY_LAYOUT
        }
    }

    override fun refresh() {
        adapter.clearItems()
        presenter.fragmentStarted(false)
    }

    override fun onRefresh() {
        if (refresher.isRefreshing) refresher.isRefreshing = false
        presenter.refreshFragment()
    }
}