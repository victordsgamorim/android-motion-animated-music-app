package com.victor.musicapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.victor.musicapp.R
import com.victor.musicapp.model.Band
import com.victor.musicapp.ui.BaseFragment
import com.victor.musicapp.ui.main.viewpager.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_album.*
import java.lang.IllegalArgumentException

class AlbumFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(fragmentContext)
        viewpager.adapter = adapter

        val lista = mutableListOf<Band>()
        lista.add(Band(1, "Fresno"))
        lista.add(Band(2, "Rua"))
        adapter.submitList(lista)

        viewpager.clipChildren = false
        viewpager.clipToPadding = false
        viewpager.offscreenPageLimit = 3
        viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
}