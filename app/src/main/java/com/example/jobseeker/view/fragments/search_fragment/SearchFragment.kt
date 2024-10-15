package com.example.jobseeker.view.fragments.search_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jobseeker.R
import com.example.jobseeker.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}