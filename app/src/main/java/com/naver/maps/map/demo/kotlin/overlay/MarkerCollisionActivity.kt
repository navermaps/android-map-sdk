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
package com.naver.maps.map.demo.kotlin.overlay

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Checkable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.activity_marker_collision.*
import java.util.ArrayList

class MarkerCollisionActivity : AppCompatActivity(), OnMapReadyCallback {
    private var forceShowIcon: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_marker_collision)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: MapFragment.newInstance().also {
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
        val markers = ArrayList<Marker>()
        val bounds = naverMap.contentBounds

        repeat(50) { i ->
            markers.add(Marker().apply {
                setImportant(this, i < 10)
                position = LatLng(
                        (bounds.northLatitude - bounds.southLatitude) * Math.random() + bounds.southLatitude,
                        (bounds.eastLongitude - bounds.westLongitude) * Math.random() + bounds.westLongitude
                )
                captionText = "Marker #$i"
                setOnClickListener {
                    val important = tag as Boolean
                    setImportant(this, !important)
                    true
                }
                map = naverMap
            })
        }

        hide_collided_symbols.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            markers.forEach {
                it.isHideCollidedSymbols = checked
            }
        }

        hide_collided_symbols.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            markers.forEach {
                it.isHideCollidedMarkers = checked
            }
        }

        hide_collided_captions.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            markers.forEach {
                it.isHideCollidedCaptions = checked
            }
        }

        force_show.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            forceShowIcon = checked
            markers.forEach {
                val important = it.tag as Boolean
                it.isForceShowIcon = important && forceShowIcon
            }
        }
    }

    private fun setImportant(marker: Marker, important: Boolean) {
        marker.icon = if (important) MarkerIcons.GREEN else MarkerIcons.GRAY
        marker.zIndex = if (important) 1 else 0
        marker.tag = important
        marker.isForceShowIcon = important && forceShowIcon
    }
}
