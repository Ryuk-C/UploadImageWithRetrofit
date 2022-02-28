package com.haznedar.uploadimage.repo

import com.haznedar.uploadimage.data.model.ImageUploadModel
import com.haznedar.uploadimage.data.repository.MainRepositoryInterface
import com.haznedar.uploadimage.util.Resource
import okhttp3.MultipartBody

class FakeRepo : MainRepositoryInterface{

    override suspend fun uploadImage(
        id: String,
        rotation: String,
        key: String,
        code: String,
        file: MultipartBody.Part?
    ): Resource<ImageUploadModel> {

        return Resource.success(ImageUploadModel("FileName", "Message",1))

    }
}