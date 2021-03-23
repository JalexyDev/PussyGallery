package com.jalexy.pussygallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jalexy.pussygallery.R

/**
 * A placeholder fragment containing a simple view.
 */
class PussyFavoriteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

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