package com.example.domain.repository

import com.google.gson.JsonObject

interface OfferVacancyRepository {
    suspend fun getFile(fileUrl: String): JsonObject
}