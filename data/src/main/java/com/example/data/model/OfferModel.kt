package com.example.data.model

data class OfferModel(
    val id: String,
    val title: String,
    val link: String,
    val buttonModel: ButtonModel? = null
)
