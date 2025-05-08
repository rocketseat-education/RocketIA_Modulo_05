package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.rocketseat.rocketia.databinding.FragmentWelcomeBinding
import com.rocketseat.rocketia.ui.viewmodel.WelcomeViewModel
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.ui.event.WelcomeUiEvent
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModel()

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
//            btnWelcomeStart.setOnClickListener {
//                findNavController().navigate(R.id.action_welcomeFragment_to_chooseStackFragment)
//            }
            composeViewWelcome.setContent {
                WelcomeButton {
                    findNavController().navigate(R.id.action_welcomeFragment_to_chooseStackFragment)
                }
            }
        }
    }

    @Composable
    private fun WelcomeButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
        Button(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC58DE7)
            ),
            shape = RoundedCornerShape(4.dp),
            onClick = onClick
        ) {
            Text(
                text = stringResource(R.string.iniciar),
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontFamily = FontFamily(Font(resId = R.font.inter)),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.hasSelectedStack?.let { hasSelectedStack ->
                        if (hasSelectedStack)
                            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
                        else {
                            binding.pbWelcomeLoading.visibility = View.GONE
                            binding.llWelcomeContainer.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Preview
    @Composable
    private fun WelcomeButtonPreview() {
        WelcomeButton {  }
    }
}