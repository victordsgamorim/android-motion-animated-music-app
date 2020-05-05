package com.victor.musicapp.presenter.ui.main.album.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.victor.musicapp.data.api.response.TrackItem

object DiffUtilCallback : DiffUtil.ItemCallback<TrackItem>() {
    override fun areItemsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
        return oldItem == newItem
    }

}