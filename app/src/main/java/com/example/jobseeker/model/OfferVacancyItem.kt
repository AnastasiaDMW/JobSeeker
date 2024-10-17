package com.example.jobseeker.model

import com.example.domain.model.ListItem

data class OfferVacancyItem(
    val offersVacancies: List<ListItem>
): ListItem
