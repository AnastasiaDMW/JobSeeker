package com.example.jobseeker.view.fragments.search_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.model.OfferVacancyResponse
import com.example.domain.model.Vacancy
import com.example.domain.use_case.GetDataFromFileUseCase
import com.example.jobseeker.model.OfferVacancyState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchViewModel @Inject constructor(
    private val getOfferVacancyUseCase: GetDataFromFileUseCase
): ViewModel() {

    private val _state = MutableLiveData<OfferVacancyState>()
    val state: LiveData<OfferVacancyState> = _state

    private var _countFavorite: Int = 0
    val countFavorite: Int
        get() = _countFavorite

    init {
        getOfferVacancy()
    }

    private fun getOfferVacancy() {
        getOfferVacancyUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val vacancies = result.data?.vacancies ?: emptyList()
                    _countFavorite = getFavoriteCount(vacancies)
                    _state.value = OfferVacancyState(
                        offerVacancy = result.data ?: OfferVacancyResponse(emptyList(), emptyList()),
                        countFavorite = _countFavorite
                    )
                }
                is Resource.Error -> {
                    _state.value = OfferVacancyState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = OfferVacancyState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateFavorite(vacancy: Vacancy) {
        _state.value?.offerVacancy?.let { response ->
            val updatedVacancies = response.vacancies.map { if (it == vacancy) it.copy(isFavorite = !it.isFavorite) else it }
            val updatedResponse = response.copy(vacancies = updatedVacancies)
            _state.value = OfferVacancyState(offerVacancy = updatedResponse)
            _state.value?.offerVacancy = updatedResponse
            _countFavorite = getFavoriteCount(updatedVacancies)
            _state.value = OfferVacancyState(
                offerVacancy = updatedResponse,
                countFavorite = _countFavorite
            )
        }
    }

    fun getFavoriteCount(vacancies: List<Vacancy>): Int {
        return vacancies.count { it.isFavorite }
    }
}