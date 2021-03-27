package com.jalexy.pussygallery.mvp.view.ui

import android.os.Bundle
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

    lateinit var  presenter: PussySearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PussySearchPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        return root
    }

    override fun loadFragment() {
        TODO("Not yet implemented")
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

    override fun loadItems() {
        TODO("Not yet implemented")
    }

    override fun finishLoading() {
        TODO("Not yet implemented")
    }

    override fun puk(str: String) {
        TODO("Not yet implemented")
    }

    override fun addImages(images: ArrayList<Image>) {
        TODO("Not yet implemented")
    }

    override fun addImage(image: Image) {
        TODO("Not yet implemented")
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }
}