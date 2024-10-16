package com.example.jobseeker.view.fragments.search_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.jobseeker.MyApplication
import com.example.jobseeker.R
import com.example.jobseeker.databinding.FragmentSearchBinding
import com.example.jobseeker.view.MainActivity
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    @Inject
    lateinit var searchViewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        searchViewModel.state.observe(requireActivity()) {
            if (!it.isLoading) {
                if (it.error.isNotBlank()) {

                } else {
                    Log.d("DATA", it.offerVacancy.toString())
                }
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