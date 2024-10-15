package com.example.jobseeker.view.fragments.responses_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobseeker.R
import com.example.jobseeker.databinding.FragmentResponsesBinding
import com.example.jobseeker.databinding.FragmentSearchBinding

class ResponsesFragment : Fragment(R.layout.fragment_responses) {

    private var binding: FragmentResponsesBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResponsesBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}