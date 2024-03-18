/*
 * Copyright 2018-2024 NAVER Corp.
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
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.ArrowheadPathOverlay

class ArrowheadPathOverlayActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(
                NaverMapOptions().camera(
                    CameraPosition(LatLng(37.5701573, 126.9777503), 14.0, 50.0, 0.0)
                )
            ).also {
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
        ArrowheadPathOverlay().apply {
            coords = COORDS_1
            width = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_width)
            color = Color.WHITE
            outlineWidth = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_outline_width)
            outlineColor = ResourcesCompat.getColor(resources, R.color.primary, theme)
            map = naverMap
        }

        ArrowheadPathOverlay().apply {
            coords = COORDS_2
            width = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_width)
            color = 0x80000000.toInt()
            outlineWidth = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_outline_width)
            outlineColor = Color.BLACK
            map = naverMap
        }

        ArrowheadPathOverlay().apply {
            coords = COORDS_2
            width = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_width)
            color = Color.WHITE
            outlineWidth = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_outline_width)
            outlineColor = ResourcesCompat.getColor(resources, R.color.primary, theme)
            elevation = resources.getDimensionPixelSize(R.dimen.arrowhead_path_overlay_elevation)
            map = naverMap
        }
    }

    companion object {
        private val COORDS_1 = listOf(
            LatLng(37.568003, 126.9782503),
            LatLng(37.5701573, 126.9782503),
            LatLng(37.5701573, 126.9803745)
        )

        private val COORDS_2 = listOf(
            LatLng(37.568003, 126.9772503),
            LatLng(37.5701573, 126.9772503),
            LatLng(37.5701573, 126.9751261)
        )
    }
}
