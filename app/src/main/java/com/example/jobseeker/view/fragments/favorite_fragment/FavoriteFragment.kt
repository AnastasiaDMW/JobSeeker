package com.example.jobseeker.view.fragments.favorite_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jobseeker.MyApplication
import com.example.jobseeker.R
import com.example.jobseeker.adapter.MainScreenDelegates
import com.example.jobseeker.databinding.FragmentFavoriteBinding
import com.example.jobseeker.model.OfferVacancyItem
import com.example.jobseeker.view.MainViewModel
import com.example.jobseeker.view.utils.FormatTextData
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    @Inject
    lateinit var mainViewModel: MainViewModel
    private var binding: FragmentFavoriteBinding? = null

    private val verticalAdapter by lazy {
        ListDelegationAdapter(MainScreenDelegates.offersVacancyVerticalDelegates)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        mainViewModel.state.observe(requireActivity()) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {
                    Log.d("ERROR", it.error)
                } else {
                    Log.d("DATA", it.offerVacancy.toString())
                    updateAdapters()
                    binding?.tvCount?.text = FormatTextData().getDeclineVacancy(mainViewModel.countFavorite)
                    Log.d("NAVIGATION", it.countFavorite.toString())
                }
            }
        }

        binding?.run {
            tvCount.text = FormatTextData().getDeclineVacancy(mainViewModel.countFavorite)
        }

        initAdapters()
    }

    private fun initAdapters() {
        binding?.run {
            rvVacancies.adapter = verticalAdapter
        }
    }

    private fun updateAdapters() {
        binding?.run {
            verticalAdapter.apply {
                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = mainViewModel.state.value?.offerVacancy?.vacancies?.filter { it.isFavorite } ?: emptyList()
                    )
                )
                notifyDataSetChanged()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}