package com.jalexy.pussygallery.mvp.model.entities

import com.google.gson.annotations.SerializedName

data class Image(
    val id: String,
    val url: String,
    @SerializedName("sub_id") val subId: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("original_filename") val originalFilename: String?,
    val categories: ArrayList<Category>,
    val breeds: ArrayList<Breed>)