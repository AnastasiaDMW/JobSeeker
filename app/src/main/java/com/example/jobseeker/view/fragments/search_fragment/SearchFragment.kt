package com.example.jobseeker.view.fragments.search_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jobseeker.MyApplication
import com.example.jobseeker.R
import com.example.jobseeker.adapter.MainScreenDelegates
import com.example.jobseeker.databinding.FragmentSearchBinding
import com.example.jobseeker.model.OfferVacancyItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    @Inject
    lateinit var searchViewModel: SearchViewModel

    private val horizontalAdapter by lazy {
        ListDelegationAdapter(MainScreenDelegates.offersVacancyHorizontalDelegates)
    }
    private val verticalAdapter by lazy {
        ListDelegationAdapter(MainScreenDelegates.offersVacancyVerticalDelegates)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        searchViewModel.state.observe(requireActivity()) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {
                    Log.d("ERROR", it.error)
                } else {
                    Log.d("DATA", it.offerVacancy.toString())
                    updateAdapters()
                    Log.d("NAVIGATION", it.countFavorite.toString())
                }
            }
        }
        MainScreenDelegates.setOnFavoriteClickListener { vacancy ->
            searchViewModel.updateFavorite(vacancy)
        }
        initAdapters()
    }

    private fun initAdapters() {
        binding?.run {
            rvOffers.adapter = horizontalAdapter
            rvVacancies.adapter = verticalAdapter
        }
    }

    private fun updateAdapters() {
        binding?.run {
            horizontalAdapter.apply {
                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = searchViewModel.state.value?.offerVacancy?.offers ?: emptyList()
                    )
                )
                notifyDataSetChanged()
            }
            verticalAdapter.apply {
                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = searchViewModel.state.value?.offerVacancy?.vacancies ?: emptyList()
                    )
                )
                notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }
}