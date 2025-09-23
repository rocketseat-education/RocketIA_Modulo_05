package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.rocketseat.rocketia.ui.compose.ChooseStackScreen
import com.rocketseat.rocketia.ui.viewmodel.ChooseStackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue


class ChooseStackComposeFragment : Fragment() {

    private val viewModel: ChooseStackViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(context = requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ChooseStackScreen(viewModel = viewModel)
            }
        }
    }

}