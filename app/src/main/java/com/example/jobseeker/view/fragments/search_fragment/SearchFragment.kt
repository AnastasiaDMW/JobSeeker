package com.example.jobseeker.view.fragments.search_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
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
                    val countVacancy = mainViewModel.state.value?.offerVacancy?.vacancies?.count()
                    binding?.tvCountVacancy?.text = FormatTextData().getDeclineVacancy(countVacancy ?: 0)
                    if (countVacancy != null) {
                        if (countVacancy > 3) {
                            binding?.btnMoreVacancy?.text = "Еще "+FormatTextData().getDeclineVacancy(countVacancy-3)
                        } else {
                            binding?.btnMoreVacancy?.visibility = View.GONE
                        }
                    }
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
            btnMoreVacancy.setOnClickListener {
                mainViewModel.isLimit = false
                updateAdapters()
            }
            horizontalAdapter.apply {
                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = mainViewModel.state.value?.offerVacancy?.offers ?: emptyList()
                    )
                )
                notifyDataSetChanged()
            }
            verticalAdapter.apply {
                val vacanciesToDisplay = if (mainViewModel.isLimit) {
                    btnMoreVacancy.visibility = View.VISIBLE
                    mainViewModel.limitState
                } else {
                    btnMoreVacancy.visibility = View.GONE
                    mainViewModel.state.value?.offerVacancy?.vacancies ?: emptyList()
                }

                items = listOf(
                    OfferVacancyItem(
                        offersVacancies = vacanciesToDisplay
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