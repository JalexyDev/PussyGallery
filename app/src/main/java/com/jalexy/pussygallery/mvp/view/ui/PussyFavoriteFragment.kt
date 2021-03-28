package com.jalexy.pussygallery.mvp.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView


class PussyFavoriteFragment : Fragment(), PussyListFragmentView {

    companion object {
        @JvmStatic
        fun newInstance(): PussyFavoriteFragment {
            return PussyFavoriteFragment()
        }
    }

    private lateinit var  presenter: PussyFavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PussyFavoritePresenter(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image_list, container, false)

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

    override fun addPussies(pussies: ArrayList<MyPussy>) {
        TODO("Not yet implemented")
    }

    override fun addPussy(pussy: MyPussy) {
        TODO("Not yet implemented")
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }
}