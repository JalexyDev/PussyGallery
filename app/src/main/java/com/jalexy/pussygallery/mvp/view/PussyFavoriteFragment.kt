package com.jalexy.pussygallery.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.presenter.PussyFavoritePresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class PussyFavoriteFragment : MvpAppCompatFragment(), FavoriteFragmentView  {

    @InjectPresenter
    lateinit var  presenter: PussyFavoritePresenter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)

        return root
    }

    companion object {

        @JvmStatic
        fun newInstance(): PussyFavoriteFragment {
            return PussyFavoriteFragment()
//            return PlaceholderFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                }
//            }
        }
    }
}