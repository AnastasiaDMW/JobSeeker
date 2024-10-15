package com.example.jobseeker.view.fragments.favorite_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jobseeker.R
import com.example.jobseeker.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}