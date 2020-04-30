package com.victor.musicapp.presenter.ui.main.viewpager.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.victor.musicapp.domain.dto.Track
import com.victor.musicapp.domain.dto.TrackItem
import com.victor.musicapp.domain.model.Band

object DiffUtilCallback : DiffUtil.ItemCallback<TrackItem>() {
    override fun areItemsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
        return oldItem == newItem
    }

}