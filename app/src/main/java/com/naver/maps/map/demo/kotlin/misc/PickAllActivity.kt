/*
 * Copyright 2018-2019 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.naver.maps.map.demo.kotlin.misc

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.Pickable
import com.naver.maps.map.Symbol
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_pick_all.*

class PickAllActivity : AppCompatActivity(), OnMapReadyCallback {
    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text: TextView = itemView as TextView

        fun setItem(item: Pickable) {
            val context = itemView.context
            when (item) {
                is Symbol -> text.text = context.getString(R.string.format_pickable_symbol, item.caption)
                is Marker -> text.text = context.getString(R.string.format_pickable_marker, item.captionText)
                else -> text.text = context.getString(R.string.pickable_overlay)
            }
        }
    }

    private class Adapter : ListAdapter<Pickable, ViewHolder>(object : DiffUtil.ItemCallback<Pickable>() {
        override fun areItemsTheSame(oldItem: Pickable, newItem: Pickable) = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Pickable, newItem: Pickable) = oldItem == newItem
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pickable, parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setItem(getItem(position))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pick_all)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: MapFragment.newInstance(NaverMapOptions().enabledLayerGroups(NaverMap.LAYER_GROUP_TRANSIT)).also {
                    supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                }
        mapFragment.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            if (item.itemId == android.R.id.home) {
                finish()
                true
            } else {
                super.onOptionsItemSelected(item)
            }

    override fun onMapReady(naverMap: NaverMap) {
        Marker().apply {
            position = LatLng(37.56752, 126.97385)
            captionText = "Marker 1"
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56211, 126.98266)
            captionText = "Marker 2"
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.563028, 126.97629)
            captionText = "Marker 3"
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56992, 126.98005)
            captionText = "Marker 4"
            map = naverMap
        }

        val radius = resources.getDimensionPixelSize(R.dimen.pick_radius)
        val adapter = Adapter()

        recycler_view.adapter = adapter

        naverMap.setOnMapClickListener { point, _ ->
            circle.x = point.x - radius
            circle.y = point.y - radius
            circle.visibility = View.VISIBLE
            adapter.submitList(naverMap.pickAll(point, radius))
        }

        naverMap.addOnCameraChangeListener { _, _ ->
            circle.visibility = View.GONE
            adapter.submitList(emptyList())
        }
    }
}
