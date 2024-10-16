package com.example.jobseeker.model

import com.example.domain.model.OfferVacancyResponse

data class OfferVacancyState(
    val isLoading: Boolean = false,
    val offerVacancy: OfferVacancyResponse = OfferVacancyResponse(emptyList(), emptyList()),
    val error: String = ""
)
