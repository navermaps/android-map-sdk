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
package com.naver.maps.map.demo.kotlin.map

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Checkable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.activity_night_mode.*

class NightModeActivity : AppCompatActivity(), OnMapReadyCallback {
    private var nightModeEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_night_mode)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: run {
                    val options = NaverMapOptions()
                            .nightModeEnabled(true)
                            .backgroundResource(NaverMap.DEFAULT_BACKGROUND_DRWABLE_DARK)
                            .mapType(NaverMap.MapType.Navi)
                            .minZoom(4.0)
                    MapFragment.newInstance(options).also {
                        supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                    }
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
        val markers = MARKER_COORDS.map { coord ->
            Marker().apply {
                position = coord
                icon = MarkerIcons.GRAY
                map = naverMap
            }
        }

        naverMap.addOnOptionChangeListener {
            if (nightModeEnabled != naverMap.isNightModeEnabled) {
                nightModeEnabled = naverMap.isNightModeEnabled

                naverMap.setBackgroundResource(run {
                    if (nightModeEnabled) {
                        NaverMap.DEFAULT_BACKGROUND_DRWABLE_DARK
                    } else {
                        NaverMap.DEFAULT_BACKGROUND_DRWABLE_LIGHT
                    }
                })

                val icon = if (nightModeEnabled) MarkerIcons.GRAY else Marker.DEFAULT_ICON

                markers.forEach { marker ->
                    marker.icon = icon
                }
            }
        }

        toggle_night_mode.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.isNightModeEnabled = checked
        }
    }

    companion object {
        private val MARKER_COORDS = arrayOf(
                LatLng(37.5666102, 126.9783881),
                LatLng(37.57000, 126.97618),
                LatLng(37.56138, 126.97970))
    }
}
