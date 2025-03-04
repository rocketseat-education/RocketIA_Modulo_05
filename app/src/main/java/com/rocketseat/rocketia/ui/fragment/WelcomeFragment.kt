package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rocketseat.rocketia.databinding.FragmentWelcomeBinding
import com.rocketseat.rocketia.ui.viewmodel.WelcomeViewModel

class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModels()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}