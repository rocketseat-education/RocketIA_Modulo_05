package com.rocketseat.rocketia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.rocketseat.rocketia.R
import com.rocketseat.rocketia.databinding.ItemAiChatBalloonBinding
import com.rocketseat.rocketia.databinding.ItemUserChatBalloonBinding
import com.rocketseat.rocketia.domain.model.AIChatText
import io.noties.markwon.Markwon

class AIChatAdapter: ListAdapter<AIChatText, AIChatAdapter.AIChatViewHolder>(
    AIChatTextDiffCallback()
) {
    class AIChatViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindQuestion(question: String) {
            with(binding as ItemUserChatBalloonBinding) {
                tvUserQuestion.text = question
            }
        }

        fun bindAnswer(answer: String) {
            with(binding as ItemAiChatBalloonBinding) {
                val markwon = Markwon.create(binding.root.context)
                markwon.setMarkdown(tvIAAnswer, answer)
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
                val userChatBalloonBinding = ItemUserChatBalloonBinding.inflate(inflater, parent, false)
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
            is AIChatText.AIAnswer -> holder.bindAnswer(aiChatText.answer)
            is AIChatText.UserQuestion -> holder.bindQuestion(aiChatText.question)
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is AIChatText.AIAnswer -> R.layout.item_ai_chat_balloon
            is AIChatText.UserQuestion -> R.layout.item_user_chat_balloon
        }
    }
}
