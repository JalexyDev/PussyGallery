package com.jalexy.pussygallery.mvp.model

class PussyApiManager(service: PussyApi) {
    val apiService = service

    fun getImages() =
        apiService.getImages()
}