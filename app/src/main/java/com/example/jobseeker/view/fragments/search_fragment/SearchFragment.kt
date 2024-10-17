package com.example.jobseeker.view.fragments.search_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.jobseeker.MyApplication
import com.example.jobseeker.R
import com.example.jobseeker.adapter.MainScreenDelegates
import com.example.jobseeker.databinding.FragmentSearchBinding
import com.example.jobseeker.model.OfferVacancyItem
import com.example.jobseeker.view.MainViewModel
import com.example.jobseeker.view.utils.FormatTextData
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    @Inject
    lateinit var mainViewModel: MainViewModel

    private val horizontalAdapter by lazy {
        ListDelegationAdapter(MainScreenDelegates.offersVacancyHorizontalDelegates)
    }
    private val verticalAdapter by lazy {
        ListDelegationAdapter(MainScreenDelegates.offersVacancyVerticalDelegates)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        mainViewModel.state.observe(requireActivity()) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {
                } else {
                    updateAdapters()
                    binding?.tvCountVacancy?.text = FormatTextData().getDeclineVacancy(mainViewModel.state.value?.offerVacancy?.vacancies?.count() ?: 0)
                }
            }
        }
        MainScreenDelegates.setOnFavoriteClickListener { vacancy ->
            mainViewModel.updateFavorite(vacancy)
        }
        initAdapters()
        initSearchListeners()
    }

    private fun initSearchListeners() {
        binding?.run {
            icSearch.setOnClickListener {
                if (lSort.visibility != View.VISIBLE) switchToBackArrow() else switchToSearchIcon()
            }

            etSearch.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    icSearch.setImageResource(R.drawable.back_arrow)
                    lSort.visibility = View.VISIBLE
                    etSearch.setHint(R.string.et_search_true)
                } else {
                    switchToSearchIcon()
                }
            }
        }
    }

    private fun switchToBackArrow() {
        binding?.run {
            icSearch.setImageResource(R.drawable.back_arrow)
            lSort.visibility = View.VISIBLE
            etSearch.requestFocus()
        }
    }

    private fun switchToSearchIcon() {
        binding?.run {
            etSearch.clearFocus()
            etSearch.setHint(R.string.et_search_false)
            icSearch.setImageResource(R.drawable.search)
            lSort.visibility = View.GONE
        }
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
                        offersVacancies = mainViewModel.state.value?.offerVacancy?.offers ?: emptyList()
                    )
                )
                notifyDataSetChanged()
            }
            verticalAdapter.apply {
                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = mainViewModel.state.value?.offerVacancy?.vacancies ?: emptyList()
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