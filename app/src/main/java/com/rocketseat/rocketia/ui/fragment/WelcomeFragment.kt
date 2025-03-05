package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rocketseat.rocketia.databinding.FragmentWelcomeBinding
import com.rocketseat.rocketia.ui.viewmodel.WelcomeViewModel
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.ui.event.WelcomeUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    // private val viewModel: WelcomeViewModel by viewModel()
    private val viewModel: WelcomeViewModel by viewModels()

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onEvent(event = WelcomeUiEvent.CheckHasSelectedStack)

        setupObservers()

        with(binding) {
            btnWelcomeStart.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_chooseStackFragment)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                uiState.hasSelectedStack?.let { hasSelectedStack ->
                    if(hasSelectedStack)
                        findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
                    else {
                        binding.pbWelcomeLoading.visibility = View.GONE
                        binding.llWelcomeContainer.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}