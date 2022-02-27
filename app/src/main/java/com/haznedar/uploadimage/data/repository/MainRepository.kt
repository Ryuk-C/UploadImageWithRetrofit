package com.haznedar.uploadimage.data.repository

import com.haznedar.uploadimage.data.remote.APIService
import com.haznedar.uploadimage.data.model.ImageUploadModel
import com.haznedar.uploadimage.util.Resource
import com.haznedar.uploadimage.util.internetCheck
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class MainRepository @Inject constructor(

    private val api : APIService

) : MainRepositoryInrerface {
    override suspend fun uploadImage(
        id: String,
        rotation: String,
        key: String,
        code: String,
        file: MultipartBody.Part?
    ): Resource<ImageUploadModel> = withContext(Dispatchers.IO){

        try {

            val process = api.uploadImage(id, rotation, key, code, file)

            if (process.isSuccessful) {

                process.body()?.let {
                    return@withContext Resource.success(it)
                } ?: return@withContext Resource.error("No data!", null)

            } else {

                return@withContext Resource.error("Service Error!", null)
            }

        } catch (e: Exception) {

            if (!internetCheck()) {
                return@withContext Resource.internet(null)
            } else {
                return@withContext Resource.error(e.localizedMessage!!, null)
            }
        }

    }

}