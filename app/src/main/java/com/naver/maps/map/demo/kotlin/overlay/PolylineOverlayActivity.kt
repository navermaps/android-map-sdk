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

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.PolylineOverlay

class PolylineOverlayActivity : AppCompatActivity(), OnMapReadyCallback {
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
        val width = resources.getDimensionPixelSize(R.dimen.overlay_line_width)
        val patternInterval = resources.getDimensionPixelSize(R.dimen.overlay_pattern_interval)

        PolylineOverlay().also {
            it.width = width
            it.coords = COORDS_1
            it.color = ResourcesCompat.getColor(resources, R.color.primary, theme)
            it.map = naverMap
        }

        PolylineOverlay().also {
            it.width = width
            it.coords = COORDS_2
            it.setPattern(patternInterval, patternInterval)
            it.color = Color.GRAY
            it.map = naverMap
        }
    }

    companion object {
        private val COORDS_1 = listOf(
                LatLng(37.57152, 126.97714),
                LatLng(37.56607, 126.98268),
                LatLng(37.56445, 126.97707),
                LatLng(37.55855, 126.97822))

        private val COORDS_2 = listOf(
                LatLng(37.57152, 126.97714),
                LatLng(37.5744287, 126.982625))
    }
}
