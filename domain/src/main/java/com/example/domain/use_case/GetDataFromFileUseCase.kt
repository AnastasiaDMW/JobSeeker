package com.example.domain.use_case

import com.example.domain.common.Constants.FILE_URL
import com.example.domain.common.Resource
import com.example.domain.model.OfferVacancyResponse
import com.example.domain.repository.OfferVacancyRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GetDataFromFileUseCase @Inject constructor(
    private val repository: OfferVacancyRepository
) {

    operator fun invoke(): Flow<Resource<OfferVacancyResponse>> = flow {
        try {
            emit(Resource.Loading())
            val jsonData = withContext(Dispatchers.IO) {
                repository.getFile(FILE_URL)
            }
            val gson = Gson()
            val response = gson.fromJson(jsonData, OfferVacancyResponse::class.java)
            emit(Resource.Success(response))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection!"))
        }
    }
}