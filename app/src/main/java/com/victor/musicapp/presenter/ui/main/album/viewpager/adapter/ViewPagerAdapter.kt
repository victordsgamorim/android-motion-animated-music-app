package com.victor.musicapp.presenter.ui.main.album.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.victor.musicapp.databinding.StartItemMotionSampleBinding
import com.victor.musicapp.domain.dto.TrackItem
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.callback.DiffUtilCallback
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.motion.MotionItemInitializer
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.motion.MotionLayoutListener
import kotlinx.android.synthetic.main.start_item_motion_sample.view.*

class ViewPagerAdapter(
    private val requestManager: RequestManager,
    var onItemClickListener: (TrackItem) -> Unit = {}
) :
    ListAdapter<TrackItem, ViewPagerAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding =
            StartItemMotionSampleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val itemBinding: StartItemMotionSampleBinding,
        context: Context
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val bindingRoot by lazy {
            itemBinding.root
        }

        private val itemAnimationInitializer by lazy {
            MotionItemInitializer(context, bindingRoot)
        }

        init {
            itemAnimationInitializer.startItemAnimation()
        }

        fun bind(track: TrackItem) {

            itemBinding.fragmentDetailAlbumCard.setOnClickListener {
                onItemClickListener(track)
            }

            StartItemMotionSampleBinding.bind(bindingRoot).run {
                requestManager.load(track.album.images[0].url)
                    .into(this.mainAlbumCover)
            }


//            EndMotionExtendedCardInfoBinding.bind(bindingRoot).run {
//                this.mainBandName.text = track.name
//            }

            bindingRoot.item_motion_layout.setTransitionListener(MotionLayoutListener)
        }
    }
}

