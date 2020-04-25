package com.victor.musicapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.victor.musicapp.R
import com.victor.musicapp.model.Band
import com.victor.musicapp.ui.recyclerview.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter =
            ViewPagerAdapter(this)
        viewpager.adapter = adapter

        val lista = mutableListOf<Band>()
        lista.add(Band(1, "Fresno"))
        lista.add(Band(2, "Rua"))
        adapter.submitList(lista)
    }


}
