package com.jalexy.pussygallery.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class PussySearchFragment : MvpAppCompatFragment(), SearchFragmentView {

    @InjectPresenter
    lateinit var  presenter: PussySearchPresenter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        return root
    }

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
}