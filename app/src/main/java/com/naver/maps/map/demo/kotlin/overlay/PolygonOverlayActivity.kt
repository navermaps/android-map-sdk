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
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.ColorUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.PolygonOverlay

class PolygonOverlayActivity : AppCompatActivity(), OnMapReadyCallback {
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
        val color = ResourcesCompat.getColor(resources, R.color.primary, theme)

        PolygonOverlay().also {
            it.coords = COORDS_1
            it.color = ColorUtils.setAlphaComponent(color, 31)
            it.outlineColor = color
            it.outlineWidth = resources.getDimensionPixelSize(R.dimen.overlay_line_width)
            it.map = naverMap
        }

        PolygonOverlay().also {
            it.coords = COORDS_2
            it.holes = HOLES
            it.color = ColorUtils.setAlphaComponent(ResourcesCompat.getColor(resources, R.color.gray, theme), 127)
            it.map = naverMap
        }
    }

    companion object {
        private val COORDS_1 = listOf(
                LatLng(37.5734571, 126.975335),
                LatLng(37.5738912, 126.9825649),
                LatLng(37.5678124, 126.9812127),
                LatLng(37.5694007, 126.9739434))

        private val COORDS_2 = listOf(
                LatLng(37.5640984, 126.9712268),
                LatLng(37.5651279, 126.9767904),
                LatLng(37.5625365, 126.9832241),
                LatLng(37.5585305, 126.9809297),
                LatLng(37.5590777, 126.974617))

        private val HOLES = listOf(listOf(
                LatLng(37.5612243, 126.9768938),
                LatLng(37.5627692, 126.9795502),
                LatLng(37.5628377, 126.976066)))
    }
}
