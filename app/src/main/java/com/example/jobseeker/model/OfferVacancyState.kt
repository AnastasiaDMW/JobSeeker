package com.example.jobseeker.model

import com.example.domain.model.OfferVacancyResponse

data class OfferVacancyState(
    val isLoading: Boolean = false,
    val countFavorite: Int = 0,
    var offerVacancy: OfferVacancyResponse = OfferVacancyResponse(emptyList(), emptyList()),
    val error: String = ""
)
