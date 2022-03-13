package com.jalexy.pussygallery.mvp.view.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.base.viewmodels.observeEvents
import com.jalexy.pussygallery.databinding.FragmentImageListBinding
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.presenter.PussySearchViewModel
import com.jalexy.pussygallery.mvp.view.models.ScreenState
import com.jalexy.pussygallery.mvp.view.ui.adapters.BaseRecyclerViewAdapter
import com.jalexy.pussygallery.mvp.view.ui.adapters.PussyRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PussySearchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {

        private const val LOAD_LAYOUT = 0
        private const val CONTENT_LAYOUT = 1
        private const val ERROR_LAYOUT = 2

        @JvmStatic
        fun newInstance(): PussySearchFragment {
            return PussySearchFragment()
        }
    }

    private val viewModel by viewModels<PussySearchViewModel>()

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PussyRecyclerViewAdapter
    private var wasLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                    return when (adapter.getItemViewType(position)) {
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

        adapter = PussyRecyclerViewAdapter(requireContext())
        adapter.retryLoadListener = { viewModel.retryLoad() }
        adapter.setFavoriteStateListener =
            { pussy, callback -> viewModel.setFavoriteState(pussy, callback) }
        adapter.favoriteClickedListener =
            { pussy, callback -> viewModel.favoriteClicked(pussy, callback) }

        binding.pussyList.adapter = adapter

        binding.pussyList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPos = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPos == totalItemCount - 1 && !adapter.hasError()) {
                    viewModel.scrolledToEnd()
                }
            }
        })

        binding.retryBtn.setOnClickListener { onRefresh() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) {
            when (it) {
                ScreenState.Content -> finishLoading()
                is ScreenState.Error -> showError()
                ScreenState.Loading -> loadFragment()
            }
        }
        viewModel.pussiesState.observe(viewLifecycleOwner) {
            addPussies(it)
        }
        viewModel.refreshEvent.observeEvents(viewLifecycleOwner) {
            refresh()
        }
        viewModel.loadItemsEvent.observeEvents(viewLifecycleOwner) {
            loadItems()
        }
        viewModel.updateItemEvent.observeEvents(viewLifecycleOwner) {
            updatePussy(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fragmentStarted(wasLoaded)
    }

    override fun onDestroy() {
        adapter.clearItems()
        super.onDestroy()
    }

    private fun updatePussy(pussy: MyPussy) {
        adapter.updateItem(pussy)
    }

    private fun loadFragment() {
        binding.flipper.displayedChild = LOAD_LAYOUT
    }

    private fun showError() {
        if (binding.refresher.isRefreshing) binding.refresher.isRefreshing = false

        if (adapter.isEmpty()) {
            binding.flipper.displayedChild = ERROR_LAYOUT
        } else {
            adapter.addError()
        }
    }

    private fun loadItems() {
        adapter.addLoader()
    }

    private fun finishLoading() {
        wasLoaded = true

        binding.flipper.displayedChild = CONTENT_LAYOUT

        adapter.removeLoader()
    }

    private fun addPussies(pussies: List<MyPussy>) {
        adapter.setPussies(pussies)
    }

    private fun refresh() {
        adapter.clearItems()
        wasLoaded = false
        viewModel.fragmentStarted(wasLoaded)
    }

    override fun onRefresh() {
        if (binding.refresher.isRefreshing)
            binding.refresher.isRefreshing = false
        viewModel.refreshFragment()
    }
}