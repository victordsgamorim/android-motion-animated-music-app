package com.victor.musicapp.presenter.ui.main.viewpager.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.victor.musicapp.domain.model.Band

object DiffUtilCallback : DiffUtil.ItemCallback<Band>() {
    override fun areItemsTheSame(oldItem: Band, newItem: Band): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Band, newItem: Band): Boolean {
        return oldItem == newItem
    }

}