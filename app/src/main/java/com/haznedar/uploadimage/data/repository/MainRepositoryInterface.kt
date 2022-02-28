package com.haznedar.uploadimage.data.repository

import com.haznedar.uploadimage.data.model.ImageUploadModel
import com.haznedar.uploadimage.util.Resource
import okhttp3.MultipartBody

interface MainRepositoryInterface {

    suspend fun uploadImage(id:String, rotation:String, key:String, code:String, file:MultipartBody.Part?) : Resource<ImageUploadModel>

}