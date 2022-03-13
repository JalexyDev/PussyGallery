package com.jalexy.pussygallery.mvp.model

import javax.inject.Inject

class PussyApiManager @Inject constructor(
    private val apiService: PussyApi
) {
    companion object {
        const val ORDER_RANDOM = "random"
        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
    }

    fun getImages(
        size: String,
        order: String,
        limit: Int,
        page: Int,
        format: String
    ) = apiService.getImages(size, order, limit, page, format)
}