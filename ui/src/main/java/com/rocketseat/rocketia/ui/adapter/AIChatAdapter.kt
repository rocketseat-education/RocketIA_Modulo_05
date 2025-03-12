package com.rocketseat.rocketia.ui.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rocketseat.rocketia.domain.model.AIChatText
import com.rocketseat.rocketia.ui.R
import com.rocketseat.rocketia.ui.databinding.ItemAiChatBalloonBinding
import com.rocketseat.rocketia.ui.databinding.ItemUserChatBalloonBinding
import io.noties.markwon.Markwon

private const val AI_ANSWER_CLIP_DATA_LABEL = "Resposta da IA copiada!"

class AIChatAdapter :
    ListAdapter<AIChatText, AIChatAdapter.AIChatViewHolder>(AIChatTextDiffCallback()) {

    class AIChatViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val clipboardManager =
            binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        fun bindQuestion(question: String) {
            with(binding as ItemUserChatBalloonBinding) {
                tvUserQuestion.text = question
            }
        }

        fun bindAnswer(answer: String) {
            with(binding as ItemAiChatBalloonBinding) {
                val markwon = Markwon.create(binding.root.context)
                markwon.setMarkdown(tvAIAnswer, answer)
                tvAIAnswer.setOnLongClickListener {
                    val clipData = ClipData.newPlainText(AI_ANSWER_CLIP_DATA_LABEL, answer)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(
                        binding.root.context,
                        AI_ANSWER_CLIP_DATA_LABEL,
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AIChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_user_chat_balloon -> {
                val userChatBalloonBinding =
                    ItemUserChatBalloonBinding.inflate(inflater, parent, false)
                AIChatViewHolder(userChatBalloonBinding)
            }

            R.layout.item_ai_chat_balloon -> {
                val aiChatBalloonBinding = ItemAiChatBalloonBinding.inflate(inflater, parent, false)
                AIChatViewHolder(aiChatBalloonBinding)
            }

            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: AIChatViewHolder,
        position: Int
    ) {
        when (val aiChatText = getItem(position)) {
            is AIChatText.AIAnswer -> holder.bindAnswer(answer = aiChatText.answer)
            is AIChatText.UserQuestion -> holder.bindQuestion(question = aiChatText.question)
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AIChatText.AIAnswer -> R.layout.item_ai_chat_balloon
            is AIChatText.UserQuestion -> R.layout.item_user_chat_balloon
        }
    }

}