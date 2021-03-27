package com.jalexy.pussygallery.mvp.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.model.entities.Image
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView


class PussySearchFragment : Fragment(), PussyListFragmentView {

    companion object {

        @JvmStatic
        fun newInstance(): PussySearchFragment {
            return PussySearchFragment()
//            return PlaceholderFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                }
//            }
        }
    }

    private lateinit var  presenter: PussySearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PussySearchPresenter(this)
        presenter.fragmentOpened()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        return root
    }

    override fun onStart() {
        super.onStart()
        presenter.fragmentStarted()
    }

    override fun loadFragment() {
        Log.d("Test", "loading")
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

    override fun loadItems() {
        TODO("Not yet implemented")
    }

    override fun finishLoading() {
        Log.d("Test", "finished load")
    }

    override fun addImages(images: ArrayList<Image>) {
        for ((i, image) in images.withIndex()) {
            Log.d("Test", "$i ${image.url}")
        }
    }

    override fun addImage(image: Image) {
        TODO("Not yet implemented")
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }
}