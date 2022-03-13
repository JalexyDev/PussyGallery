package com.jalexy.pussygallery.mvp.view.models

sealed class ScreenState {

    object Loading: ScreenState()

    object Content: ScreenState()

    data class Error(val message: String = ""): ScreenState()
}
