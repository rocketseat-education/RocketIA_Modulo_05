package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.rocketseat.rocketia.ui.R
import com.rocketseat.rocketia.ui.databinding.FragmentChooseStackBinding
import com.rocketseat.rocketia.ui.event.ChooseStackUiEvent
import com.rocketseat.rocketia.ui.viewmodel.ChooseStackViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseStackFragment : Fragment() {

    private val viewModel: ChooseStackViewModel by viewModel()

    private var _binding: FragmentChooseStackBinding? = null
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseStackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            setupStackChips()

            btnChooseStackConfirm.setOnClickListener {
                findNavController().navigate(R.id.action_chooseStackFragment_to_homeFragment)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStackChipId.collect { selectedStackChipId ->
                        selectedStackChipId?.let {
                            binding.changeSelectedStack(selectedStackChipId = selectedStackChipId)
                        }
                    }
                }

                launch {
                    viewModel.isConfirmedNewStack.collect { isConfirmedNewStack ->
                        binding.btnChooseStackConfirm.isEnabled = isConfirmedNewStack
                    }
                }
            }
        }
    }

    private fun FragmentChooseStackBinding.setupStackChips() {
        flwChooseStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip.setOnClickListener {
                viewModel.onEvent(
                    event = ChooseStackUiEvent.SelectStack(
                        selectedStackName = stackChip.text.toString(),
                        selectedStackChipId = stackChipId
                    )
                )
            }
        }
    }

    private fun FragmentChooseStackBinding.changeSelectedStack(selectedStackChipId: Int) {
        flwChooseStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip?.apply {
                setChipStrokeColorResource(
                    if (stackChip.id == selectedStackChipId)
                        R.color.white
                    else
                        R.color.border_default
                )
                isChecked = stackChip.id == selectedStackChipId
            }
        }
    }

}