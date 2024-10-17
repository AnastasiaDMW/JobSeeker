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

    private val horizontalAdapter = ListDelegationAdapter(
        MainScreenDelegates.offersVacancyHorizontalDelegates
    )

    private val verticalAdapter = ListDelegationAdapter(
        MainScreenDelegates.offersVacancyVerticalDelegates
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        searchViewModel.state.observe(requireActivity()) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {

                } else {
                    Log.d("DATA", it.offerVacancy.toString())
                    initAdapters()
                }
            }
        }
    }

    private fun initAdapters() {
        binding?.run {
            rvOffers.adapter = horizontalAdapter
            horizontalAdapter.apply {
                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = searchViewModel.state.value?.offerVacancy?.offers ?: emptyList()
                    )
                )
                notifyDataSetChanged()
            }
            rvVacancies.adapter = verticalAdapter
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