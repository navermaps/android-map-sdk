/*
 * Copyright 2018-2023 NAVER Corp.
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
package com.naver.maps.map.demo.kotlin.event

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons

class OverlayClickEventActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

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
        Marker().apply {
            position = LatLng(37.57207, 126.97917)
            captionText = getString(R.string.consume_event)
            setOnClickListener {
                icon = if (Marker.DEFAULT_ICON == icon) {
                    MarkerIcons.GRAY
                } else {
                    Marker.DEFAULT_ICON
                }
                true
            }
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56361, 126.97439)
            captionText = getString(R.string.propagate_event)
            setOnClickListener {
                icon = if (Marker.DEFAULT_ICON == icon) {
                    MarkerIcons.GRAY
                } else {
                    Marker.DEFAULT_ICON
                }
                false
            }
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56671, 126.98260)
            captionText = getString(R.string.no_event_listener)
            map = naverMap
        }

        naverMap.setOnMapClickListener { _, coord ->
            Toast.makeText(
                this,
                getString(R.string.format_map_click, coord.latitude, coord.longitude),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
