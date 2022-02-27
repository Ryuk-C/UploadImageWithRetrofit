package com.haznedar.uploadimage.data.model


import com.google.gson.annotations.SerializedName

data class ImageUploadModel(
    @SerializedName("dosyaadi")
    val dosyaadi: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Int
)