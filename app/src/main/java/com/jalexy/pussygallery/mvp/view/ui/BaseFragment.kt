package com.jalexy.pussygallery.mvp.view.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    companion object {
        const val LOAD_LAYOUT = 0
        const val CONTENT_LAYOUT = 1
        const val ERROR_LAYOUT = 2
        const val EMPTY_LAYOUT = 3
    }
}