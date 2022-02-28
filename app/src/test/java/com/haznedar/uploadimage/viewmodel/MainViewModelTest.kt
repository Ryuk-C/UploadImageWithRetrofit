package com.haznedar.uploadimage.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.haznedar.beeglobaladmin.tools.MainCoroutineRule
import com.haznedar.uploadimage.presentation.viewmodel.MainViewModel
import com.haznedar.uploadimage.repo.FakeRepo
import com.haznedar.uploadimage.tools.getOrAwaitValueTest
import com.haznedar.uploadimage.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MultipartBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel : MainViewModel

    @Before
    fun setup(){

        viewModel = MainViewModel(FakeRepo())

    }

    @Test
    fun `upload image return success`(){

        val file : MultipartBody.Part? = null

        viewModel.uploadImage("url","rotation","key ","code", file)
        val value = viewModel.uploadImageLiveData.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.SUCCESS)

    }

    @Test
    fun `upload image without someone parameters`(){

        val file : MultipartBody.Part? = null

        viewModel.uploadImage("url"," ","beekey ","", file)
        val value = viewModel.uploadImageLiveData.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `upload image return error`(){

        val file : MultipartBody.Part? = null

        viewModel.uploadImage(""," ",""," ", file)
        val value = viewModel.uploadImageLiveData.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

}