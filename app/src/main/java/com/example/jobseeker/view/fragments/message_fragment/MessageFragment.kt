package com.example.jobseeker.view.fragments.message_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobseeker.R
import com.example.jobseeker.databinding.FragmentMessageBinding

class MessageFragment : Fragment(R.layout.fragment_message) {

    private var binding: FragmentMessageBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMessageBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}