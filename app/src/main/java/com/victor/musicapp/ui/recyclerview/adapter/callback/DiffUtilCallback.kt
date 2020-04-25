package com.victor.musicapp.ui.recyclerview.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.victor.musicapp.model.Band

object DiffUtilCallback : DiffUtil.ItemCallback<Band>() {
    override fun areItemsTheSame(oldItem: Band, newItem: Band): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Band, newItem: Band): Boolean {
        return oldItem == newItem
    }

}