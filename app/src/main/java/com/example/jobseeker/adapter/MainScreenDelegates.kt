package com.example.jobseeker.adapter

import android.view.View
import com.example.domain.model.ListItem
import com.example.domain.model.Offer
import com.example.domain.model.Vacancy
import com.example.jobseeker.R
import com.example.jobseeker.databinding.ItemOfferBinding
import com.example.jobseeker.databinding.ItemOfferHorizontalBinding
import com.example.jobseeker.databinding.ItemVacancyBinding
import com.example.jobseeker.databinding.ItemVacancyVerticalBinding
import com.example.jobseeker.model.OfferVacancyItem
import com.example.jobseeker.view.fragments.search_fragment.FormatTextData
import com.example.jobseeker.view.fragments.search_fragment.SearchViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import javax.inject.Inject

object MainScreenDelegates {

    val offersVacancyHorizontalDelegates = adapterDelegateViewBinding<OfferVacancyItem, ListItem, ItemOfferHorizontalBinding>(
        { inflater, container ->
            ItemOfferHorizontalBinding.inflate(inflater, container, false).apply {
                rvHorizontal.adapter = ListDelegationAdapter(
                    offerDelegate
                )
            }
        }
    ) {
        bind {
            (binding.rvHorizontal.adapter as ListDelegationAdapter<List<ListItem>>).apply {
                items = item.offersVacancies
                notifyDataSetChanged()
            }
        }
    }

    val offersVacancyVerticalDelegates = adapterDelegateViewBinding<OfferVacancyItem, ListItem, ItemVacancyVerticalBinding>(
        { inflater, container ->
            ItemVacancyVerticalBinding.inflate(inflater, container, false).apply {
                rvVertical.adapter = ListDelegationAdapter(
                    vacancyDelegate
                )
            }
        }
    ) {
        bind {
            (binding.rvVertical.adapter as ListDelegationAdapter<List<ListItem>>).apply {
                items = item.offersVacancies
                notifyDataSetChanged()
            }
        }
    }

    private val offerDelegate = adapterDelegateViewBinding<Offer, ListItem, ItemOfferBinding>(
        { inflater, container ->
            ItemOfferBinding.inflate(inflater, container, false)
        }
    ) {
        bind {
            binding.ivIcon.setImageDrawable(
                when (item.id ?: "") {
                    "near_vacancies" -> { getDrawable(R.drawable.near_vacancies) }
                    "level_up_resume" -> { getDrawable(R.drawable.level_up_resume) }
                    "temporary_job" -> { getDrawable(R.drawable.temporary_job) }
                    else -> { getDrawable(R.drawable.near_vacancies) }
                }
            )
            binding.ivIcon.setBackgroundResource(
                when (item.id ?: "") {
                    "near_vacancies" -> R.drawable.rounded_background_blue
                    "level_up_resume", "temporary_job" -> R.drawable.rounded_background_green
                    else -> R.drawable.rounded_background_blue
                }
            )
            binding.tvTitle.text = item.title
            binding.tvAction.text = item.button?.text ?: ""
        }
    }

    private val vacancyDelegate = adapterDelegateViewBinding<Vacancy, ListItem, ItemVacancyBinding>(
        { inflater, container ->
            ItemVacancyBinding.inflate(inflater, container, false)
        }
    ) {
        bind {
            if (item.lookingNumber != null) {
               val person = FormatTextData().getDeclensePersonWord(item.lookingNumber!!)
                binding.tvLookingNumber.text = "Сейчас просматривает ${item.lookingNumber} $person"
            } else {
                binding.tvLookingNumber.text = "Сейчас просматривает 0 человек"
            }
            binding.tvTitle.text = item.title
            binding.ivIsFavorite.setImageDrawable(
                if (item.isFavorite) getDrawable(R.drawable.favorite_blue) else getDrawable(R.drawable.favorite_gray)
            )
            binding.ivIsFavorite.setOnClickListener {
                onFavoriteClick(item)
                item.isFavorite = !item.isFavorite
                binding.ivIsFavorite.setImageDrawable(
                    if (item.isFavorite) getDrawable(R.drawable.favorite_blue) else getDrawable(R.drawable.favorite_gray)
                )
            }
            binding.tvDatePublic.text = FormatTextData().getFormatDate(item.publishedDate)
            binding.tvJobExperience.text = item.experience.previewText
            binding.tvSalary.text = item.salary.short
            binding.tvSalary.visibility = if (item.salary.short == null) View.GONE else View.VISIBLE
            binding.tvTitleTown.text = item.address.town
            binding.tvCompany.text = item.company
        }
    }

    private var onFavoriteClick: (Vacancy) -> Unit = {}

    fun setOnFavoriteClickListener(listener: (Vacancy) -> Unit) {
        onFavoriteClick = listener
    }
}