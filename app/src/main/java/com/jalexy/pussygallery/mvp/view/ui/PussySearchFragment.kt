package com.jalexy.pussygallery.mvp.view.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.databinding.FragmentImageListBinding
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import com.jalexy.pussygallery.mvp.view.PussySearchFragmentView
import com.jalexy.pussygallery.mvp.view.ui.adapters.BaseRecyclerViewAdapter
import com.jalexy.pussygallery.mvp.view.ui.adapters.PussyRecyclerViewAdapter
import javax.inject.Inject


class PussySearchFragment : Fragment(), PussySearchFragmentView,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {

        private const val LOAD_LAYOUT = 0
        private const val CONTENT_LAYOUT = 1
        private const val ERROR_LAYOUT = 2

        @JvmStatic
        fun newInstance(): PussySearchFragment {
            return PussySearchFragment()
        }
    }

    @Inject
    protected lateinit var presenter: PussySearchPresenter

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PussyRecyclerViewAdapter
    private var wasLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refresher.setOnRefreshListener(this)
        binding.refresher.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary)

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

            binding.pussyList.layoutManager = layoutManager

        } else {
            (activity as AppCompatActivity).supportActionBar?.show()
            binding.pussyList.layoutManager = LinearLayoutManager(context)
        }


        binding.pussyList.itemAnimator = DefaultItemAnimator()

        adapter = PussyRecyclerViewAdapter(requireContext(), presenter)
        binding.pussyList.adapter = adapter

        binding.pussyList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPos == totalItemCount - 1 && !adapter.hasError()) {
                    presenter.scrolledToEnd()
                }
            }
        })

        binding.retryBtn.setOnClickListener { onRefresh() }

        presenter.fragmentOpened()
    }

    override fun onStart() {
        super.onStart()
        presenter.fragmentStarted(wasLoaded)
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
        binding.flipper.displayedChild = LOAD_LAYOUT
    }

    override fun showError() {
        if (binding.refresher.isRefreshing) binding.refresher.isRefreshing = false

        if (adapter.isEmpty()) {
            binding.flipper.displayedChild = ERROR_LAYOUT
        } else {
            adapter.addError()
        }
    }

    override fun loadItems() {
        adapter.addLoader()
    }

    override fun finishLoading() {
        wasLoaded = true

        binding.flipper.displayedChild = CONTENT_LAYOUT

        adapter.removeLoader()
    }

    override fun addPussies(pussies: ArrayList<MyPussy>) {
        adapter.addItems(pussies)
    }

    override fun refresh() {
        adapter.clearItems()
        wasLoaded = false
        presenter.fragmentStarted(wasLoaded)
    }

    override fun onRefresh() {
        if (binding.refresher.isRefreshing)
            binding.refresher.isRefreshing = false
        presenter.refreshFragment()
    }
}