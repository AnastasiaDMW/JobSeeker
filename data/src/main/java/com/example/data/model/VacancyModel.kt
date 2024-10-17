package com.example.data.model

data class VacancyModel(
    val id: String,
    val lookingNumber: Int? = null,
    val title: String,
    val addressModel: AddressModel,
    val company: String,
    val experienceModel: ExperienceModel,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salaryModel: SalaryModel,
    val schedules: List<String>,
    val appliedNumber: Int? = null,
    val description: String? = null,
    val responsibilities: String,
    val questions: List<String>
)
