package com.rocketseat.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.rocketseat.rocketia.ui.R
import com.rocketseat.rocketia.ui.adapter.AIChatAdapter
import com.rocketseat.rocketia.ui.databinding.FragmentAiChatBinding
import com.rocketseat.rocketia.ui.event.AIChatEvent
import com.rocketseat.rocketia.ui.extension.gone
import com.rocketseat.rocketia.ui.extension.hideKeyboard
import com.rocketseat.rocketia.ui.extension.visible
import com.rocketseat.rocketia.ui.viewmodel.AIChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AIChatFragment : Fragment() {

    private val viewModel: AIChatViewModel by viewModel()

    private var _binding: FragmentAiChatBinding? = null
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
        _binding = FragmentAiChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            clAIChatContainer.setOnClickListener {
                clearQuestionTextInput()
            }

            val userSettingsPopMenu = PopupMenu(requireContext(), ibUserSettings)
            userSettingsPopMenu.setupUserSettingsPopMenu()

            ibUserSettings.setOnClickListener {
                userSettingsPopMenu.show()
            }

            binding.rvStudyAIChat.adapter = AIChatAdapter()

            tietAIQuestion.doOnTextChanged { _, _, _, _ ->
                if (tilAIQuestion.error != null)
                    tilAIQuestion.error = null
            }

            btnSendAIQuestion.setOnClickListener {
                val questionText = tietAIQuestion.text.toString()
                if (questionText.isNotEmpty()) {
                    showLoadingAIChat()

                    viewModel.onEvent(
                        event = AIChatEvent.SendUserQuestionToAI(
                            question = questionText
                        )
                    )

                    clearQuestionTextInput()
                } else {
                    tilAIQuestion.error = getString(R.string.campo_obrigatorio)
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
                                R.string.ola_dev, selectedStack
                            )
                            binding.tilAIQuestion.hint = getString(
                                R.string.qual_a_sua_duvida_sobre,
                                selectedStack
                            )
                        }
                    }
                }

                launch {
                    viewModel.aiChatBySelectedStack
                        .collect { aiChatBySelectedStack ->
                            val aiChatAdapter = binding.rvStudyAIChat.adapter as? AIChatAdapter
                            aiChatAdapter?.apply {
                                submitList(aiChatBySelectedStack)

                                binding.rvStudyAIChat.smoothScrollToPosition(0)
                                delay(200)
                                binding.showLoadedAIChat()
                            }
                        }
                }
            }
        }
    }

    private fun PopupMenu.setupUserSettingsPopMenu() {
        this.menuInflater.inflate(R.menu.user_settings_menu, this.menu)

        this.setOnMenuItemClickListener { itemMenu ->
            when(itemMenu.itemId) {
                R.id.action_change_stack -> {
                    requireActivity().findNavController(R.id.fcvMainContainer).navigate(R.id.action_homeFragment_to_chooseStackFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun FragmentAiChatBinding.clearQuestionTextInput() {
        tilAIQuestion.clearFocus()
        tietAIQuestion.text = null
        this.root.hideKeyboard()
    }

    private fun FragmentAiChatBinding.showLoadingAIChat() {
        pbAIChatLoading.visible()
        rvStudyAIChat.gone()
    }

    private fun FragmentAiChatBinding.showLoadedAIChat() {
        pbAIChatLoading.gone()
        rvStudyAIChat.visible()
    }
}