package com.example.data.api

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleDriveService {
    @GET
    suspend fun getFile(@Url fileUrl: String): JsonObject
}