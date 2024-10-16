package com.example.jobseeker.view.fragments.search_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Resource
import com.example.domain.model.OfferVacancyResponse
import com.example.domain.use_case.GetDataFromFileUseCase
import com.example.jobseeker.model.OfferVacancyState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getOfferVacancyUseCase: GetDataFromFileUseCase
): ViewModel() {

    private val _state = MutableLiveData<OfferVacancyState>()
    val state: LiveData<OfferVacancyState> = _state

    init {
        getOfferVacancy()
        Log.d("DATA", _state.value?.offerVacancy.toString())
    }

    private fun getOfferVacancy() {
        getOfferVacancyUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = OfferVacancyState(offerVacancy = result.data ?: OfferVacancyResponse(emptyList(), emptyList()))
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
}