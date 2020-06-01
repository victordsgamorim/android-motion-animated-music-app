package com.victor.musicapp.presenter.ui.main.album.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.victor.musicapp.databinding.ItemMotionClosedCardBinding
import com.victor.musicapp.domain.dto.TrackItem
import com.victor.musicapp.presenter.util.getDominantColourFromImage
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.callback.DiffUtilCallback
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.motion.MotionItemInitializer
import com.victor.musicapp.presenter.ui.main.album.viewpager.adapter.motion.MotionLayoutListener
import com.victor.musicapp.presenter.util.GlideImageLoader
import kotlinx.android.synthetic.main.item_motion_closed_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewPagerAdapter(
    private val imageLoader: GlideImageLoader,
    var onItemClickListener: (TrackItem) -> Unit = {}
) :
    ListAdapter<TrackItem, ViewPagerAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding =
            ItemMotionClosedCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val itemBinding: ItemMotionClosedCardBinding,
        private val context: Context
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

            ItemMotionClosedCardBinding.bind(bindingRoot).run {

                /**image dominant colour*/
                CoroutineScope(Dispatchers.IO).launch {
                    getDominantColourFromImage(
                        context,
                        track.album.images[0].url
                    ) { colour ->
                        fragmentDetailAlbumCard.setCardBackgroundColor(colour)
                    }
                }

                imageLoader.setUrlToImage(
                    track.album.images[0].url,
                    mainAlbumCover
                )
            }

            bindingRoot.item_motion_layout.setTransitionListener(MotionLayoutListener)
        }
    }
}

