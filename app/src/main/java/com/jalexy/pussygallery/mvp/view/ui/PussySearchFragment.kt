package com.jalexy.pussygallery.mvp.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import com.jalexy.pussygallery.mvp.view.PussySearchFragmentView
import com.jalexy.pussygallery.mvp.view.ui.adapters.PussyRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_image_list.view.*


class PussySearchFragment : Fragment(), PussySearchFragmentView, SwipeRefreshLayout.OnRefreshListener {

    companion object {

        private const val LOAD_LAYOUT = 0
        private const val CONTENT_LAYOUT = 1
        private const val ERROR_LAYOUT = 2

        @JvmStatic
        fun newInstance(): PussySearchFragment {
            return PussySearchFragment()
        }
    }

    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var flipper: ViewFlipper
    private lateinit var pussyListView: RecyclerView
    private lateinit var errorText: TextView
    private lateinit var retryBtn: Button

    private lateinit var presenter: PussySearchPresenter
    private lateinit var adapter: PussyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PussySearchPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image_list, container, false)

        refresher = root.refresher
        refresher.setOnRefreshListener(this)

        flipper = root.flipper

        pussyListView = root.pussy_list
        pussyListView.layoutManager = LinearLayoutManager(context)
        pussyListView.itemAnimator = DefaultItemAnimator()

        adapter = PussyRecyclerViewAdapter(context!!, presenter)
        pussyListView.adapter = adapter

        pussyListView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPos == totalItemCount - 1 && !adapter.hasError()){
                    presenter.scrolledToEnd()
                }
            }
        })

        errorText = root.error_text
        retryBtn = root.retry_btn
        retryBtn.setOnClickListener { onRefresh() }

        presenter.fragmentOpened()

        return root
    }

    override fun onStart() {
        super.onStart()
        presenter.fragmentStarted()
    }

    override fun onDestroy() {
        presenter.destroy()
        adapter.clearItems()
        super.onDestroy()
    }

    override fun updatePussy(pussy: MyPussy) {
        adapter.updateItem(pussy)
    }

    override fun loadFragment() {
        Log.d("Test", "loading")
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
        Log.d("Test", "finished load")

        flipper.displayedChild = CONTENT_LAYOUT

        adapter.removeLoader()
    }

    override fun addPussies(pussies: ArrayList<MyPussy>) {
        adapter.addItems(pussies)
    }

    override fun refresh() {
        adapter.clearItems()
        presenter.fragmentStarted()
    }

    override fun onRefresh() {
        if (refresher.isRefreshing) refresher.isRefreshing = false
        presenter.refreshFragment()
    }
}