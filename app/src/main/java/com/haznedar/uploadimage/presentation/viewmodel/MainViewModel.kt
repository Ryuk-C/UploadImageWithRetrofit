package com.haznedar.uploadimage.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haznedar.uploadimage.data.model.ImageUploadModel
import com.haznedar.uploadimage.data.repository.MainRepositoryInrerface
import com.haznedar.uploadimage.util.Resource
import com.haznedar.uploadimage.util.ValueChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val repo: MainRepositoryInrerface

) : ViewModel() {

    private val uploadImageLiveData_ = MutableLiveData<Resource<ImageUploadModel>>()
    val uploadImageLiveData: LiveData<Resource<ImageUploadModel>> get() = uploadImageLiveData_

    fun uploadImage(id:String, rotation:String, key:String, code:String, file:MultipartBody.Part?
    ) {

        uploadImageLiveData_.value = Resource.loading(null)

        if (!ValueChecker(id) || !ValueChecker(rotation) || !ValueChecker(key) || !ValueChecker(code)) {

            uploadImageLiveData_.postValue(Resource.error("Found empty value!", null))

            return

        }

        viewModelScope.launch {

            val response = repo.uploadImage(id, rotation, key, code, file)

            uploadImageLiveData_.value = response

        }
    }
}