package com.sentigo.bangkit.sentigoapp.di

import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.api.ApiConfig

object Injection {
    fun providerRepository() : AppRepository {
        val apiService = ApiConfig.getApiService()

        return AppRepository.getInstance(apiService)
    }
}