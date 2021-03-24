package com.jalexy.pussygallery.mvp.model

import com.google.gson.annotations.SerializedName

data class Favorite(
    val id: String,
    @SerializedName("image_id") val imageId: String,
    @SerializedName("sub_id") val subId: String,
    @SerializedName("created_at") val createdAt: String
)