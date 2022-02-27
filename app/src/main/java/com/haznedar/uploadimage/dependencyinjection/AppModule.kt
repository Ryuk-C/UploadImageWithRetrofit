package com.haznedar.uploadimage.dependencyinjection

import com.haznedar.uploadimage.data.remote.APIService
import com.haznedar.uploadimage.data.repository.MainRepository
import com.haznedar.uploadimage.data.repository.MainRepositoryInrerface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun mainRepository(api: APIService) = MainRepository(api) as MainRepositoryInrerface

    @Singleton
    @Provides
    fun injectBackendRetrofitApi(): APIService {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("Your awesome base url")
            .build()
            .create(APIService::class.java)

    }

}