package com.example.data.extensions

import com.example.data.model.OfferModel
import com.example.domain.model.Offer

fun OfferModel.toOffer(): Offer {
    return Offer(
        id = id,
        title = title,
        link = link
    )
}