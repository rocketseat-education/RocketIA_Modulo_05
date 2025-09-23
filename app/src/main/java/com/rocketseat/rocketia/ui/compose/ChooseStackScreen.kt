package com.rocketseat.rocketia.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.databinding.FragmentChooseStackBinding
import com.rocketseat.rocketia.ui.event.ChooseStackUiEvent
import com.rocketseat.rocketia.ui.viewmodel.ChooseStackViewModel

@Composable
fun ChooseStackScreen(modifier: Modifier = Modifier, viewModel: ChooseStackViewModel) {
    val selectedStackChipId by viewModel.selectedStackChipId.collectAsStateWithLifecycle()
    val isConfirmedNewStack by viewModel.isConfirmedNewStack.collectAsStateWithLifecycle()

    AndroidViewBinding(
        modifier = modifier.fillMaxSize(),
        factory = FragmentChooseStackBinding::inflate
    ) {
        this.setupStackChips(
            onStackChipClick = { chip, chipId ->
                viewModel.onEvent(
                    event = ChooseStackUiEvent.SelectStack(
                        selectedStackName = chip.text.toString(),
                        selectedStackChipId = chipId
                    )
                )
            }
        )

        btnChooseStackConfirm.setOnClickListener {
            root.findNavController().navigate(R.id.action_chooseStackFragment_to_homeFragment)
        }

        btnChooseStackConfirm.isEnabled = isConfirmedNewStack

        selectedStackChipId?.let { selectedStackChipId ->
            this.changeSelectedStack(selectedStackChipId = selectedStackChipId)
        }
    }
}

private fun FragmentChooseStackBinding.setupStackChips(onStackChipClick: (Chip, Int) -> Unit) {
    flwChooseStackOptions.referencedIds.forEach { stackChipId ->
        val stackChip = root.findViewById<Chip>(stackChipId)

        stackChip.setOnClickListener {

            onStackChipClick(stackChip, stackChipId)
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