package com.victor.musicapp.ui.main.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victor.musicapp.R
import com.victor.musicapp.model.Band
import com.victor.musicapp.ui.main.viewpager.adapter.callback.DiffUtilCallback
import com.victor.musicapp.ui.main.viewpager.adapter.motion.MotionItemInitializer
import com.victor.musicapp.ui.main.viewpager.adapter.motion.MotionLayoutListener
import kotlinx.android.synthetic.main.end_motion_extended_card_info.view.*
import kotlinx.android.synthetic.main.start_item_motion_band.view.*

class ViewPagerAdapter
    : ListAdapter<Band, ViewPagerAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.start_item_motion_band, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        private val itemAnimationInitializer by lazy {
            MotionItemInitializer(context, itemView)
        }

        init {
            itemAnimationInitializer.startItemAnimation()
        }

        fun bind(item: Band) {
            with(itemView) {
                item_motion_layout.setTransitionListener(MotionLayoutListener)
                main_band_name.text = item.name
            }

        }
    }
}

