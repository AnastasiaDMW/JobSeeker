package com.example.data.di

import com.example.data.api.GoogleDriveService
import com.example.data.repository.OfferVacancyRepositoryImpl
import com.example.domain.common.Constants.BASE_URL
import com.example.domain.repository.OfferVacancyRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun provideGoogleDriveService(): GoogleDriveService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleDriveService::class.java)

    @Provides
    @Singleton
    fun provideOfferVacancyRepository(api: GoogleDriveService): OfferVacancyRepository = OfferVacancyRepositoryImpl(api)
}