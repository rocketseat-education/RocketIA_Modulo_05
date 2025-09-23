package com.rocketseat.rocketia.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rocketseat.rocketia.domain.model.AIChatText

class AIChatTextDiffCallback : DiffUtil.ItemCallback<AIChatText>() {

    override fun areItemsTheSame(
        oldItem: AIChatText,
        newItem: AIChatText
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AIChatText,
        newItem: AIChatText
    ): Boolean {
        return oldItem == newItem
    }

}