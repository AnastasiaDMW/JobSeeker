package com.example.data.repository

import com.example.data.api.GoogleDriveService
import com.example.domain.repository.OfferVacancyRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class OfferVacancyRepositoryImpl @Inject constructor(
    private val api: GoogleDriveService
): OfferVacancyRepository {
    override suspend fun getFile(fileUrl: String): JsonObject {
        return api.getFile(fileUrl)
    }
}