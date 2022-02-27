package com.haznedar.uploadimage.data.remote

import com.haznedar.uploadimage.data.model.ImageUploadModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @Multipart
    @POST("Your URL")
    suspend fun uploadImage(
        @Query("P1ID") P1ID: String,
        @Query("P2Rotation") P2Rotation: String,
        @Query("P3Key") P3Key: String,
        @Query("P4Kod") P4Kod: String,
        @Part file : MultipartBody.Part?,
    ): Response<ImageUploadModel>

}