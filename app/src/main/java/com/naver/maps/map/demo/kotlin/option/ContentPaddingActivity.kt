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
package com.naver.maps.map.demo.kotlin.option

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker

class ContentPaddingActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content_padding)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(
                NaverMapOptions()
                    .camera(CameraPosition(COORD_1, NaverMap.DEFAULT_CAMERA_POSITION.zoom))
                    .contentPadding(
                        resources.getDimensionPixelSize(R.dimen.map_padding_left),
                        resources.getDimensionPixelSize(R.dimen.map_padding_top),
                        resources.getDimensionPixelSize(R.dimen.map_padding_right),
                        resources.getDimensionPixelSize(R.dimen.map_padding_bottom))
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
        Marker().apply {
            position = COORD_1
            map = naverMap
        }

        Marker().apply {
            position = COORD_2
            map = naverMap
        }

        var flag = false
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val coord = if (flag) COORD_1 else COORD_2
            naverMap.moveCamera(CameraUpdate.scrollTo(coord).animate(CameraAnimation.Fly, 3000))
            flag = !flag
        }

        val contentBounds = findViewById<TextView>(R.id.content_bounds)
        val coveringBounds = findViewById<TextView>(R.id.covering_bounds)
        naverMap.addOnCameraChangeListener { _, _ ->
            val content = naverMap.contentBounds
            contentBounds.text = getString(
                R.string.format_bounds,
                content.southLatitude,
                content.westLongitude,
                content.northLatitude,
                content.eastLongitude
            )

            val covering = naverMap.coveringBounds
            coveringBounds.text = getString(
                R.string.format_bounds,
                covering.southLatitude,
                covering.westLongitude,
                covering.northLatitude,
                covering.eastLongitude
            )
        }
    }

    companion object {
        private val COORD_1 = LatLng(37.5666102, 126.9783881)
        private val COORD_2 = LatLng(35.1798159, 129.0750222)
    }
}
