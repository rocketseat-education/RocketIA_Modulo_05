package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rocketseat.rocketia.databinding.FragmentAiChatBinding
import com.rocketseat.rocketia.ui.viewmodel.AIChatViewModel
import kotlinx.coroutines.launch
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.ui.event.AIChatEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class AIChatFragment : Fragment() {

    private val viewModel: AIChatViewModel by viewModel()

    private var _binding: FragmentAiChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAiChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            tietAIQuestion.doOnTextChanged { _, _, _, _, ->
                if(tilAIQuestion.error != null)
                    tilAIQuestion.error = null
            }

            btnSendAIQuestion.setOnClickListener {
                val questionText = tietAIQuestion.text.toString()
                if (questionText.isNotEmpty()) {
                    viewModel.onEvent(
                        AIChatEvent.SendUserQuestionToAI(
                            question = questionText
                        )
                    )
                } else {
                    tietAIQuestion.error = getString(R.string.campo_obrigatorio)
                }
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStack.collect { selectedStack ->
                        selectedStack?.let {
                            binding.tvHelloWhichStackAreYouGoingToStudy.text = getString(
                                R.string.ola_dev,
                                selectedStack
                            )
                            binding.tilAIQuestion.hint = getString(
                                R.string.qual_a_sua_duvida_sobre,
                                selectedStack
                            )
                        }
                    }
                }
                launch {
                    viewModel.aiChatBySelectStack.collect { aiChatBySelectStack ->
                        Toast.makeText(
                            requireContext(),
                            "${aiChatBySelectStack.size}",
                            Toast.LENGTH_SHORT
                        ).show()
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