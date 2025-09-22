/*
 * Copyright 2018-2025 NAVER Corp.
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
package com.naver.maps.map.demo.kotlin.camera

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.demo.ToolbarActivity
import com.naver.maps.map.overlay.Marker

class FitBoundsActivity : ToolbarActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fab)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        Marker().apply {
            position = BOUNDS_1.northEast
            map = naverMap
        }

        Marker().apply {
            position = BOUNDS_1.southWest
            map = naverMap
        }

        Marker().apply {
            position = BOUNDS_2.northEast
            map = naverMap
        }

        Marker().apply {
            position = BOUNDS_2.southWest
            map = naverMap
        }

        val padding = resources.getDimensionPixelSize(R.dimen.fit_bounds_padding)
        var flag = false
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val bounds = if (flag) BOUNDS_2 else BOUNDS_1
            naverMap.moveCamera(CameraUpdate.fitBounds(bounds, padding).animate(CameraAnimation.Fly, 5000))
            flag = !flag
        }
    }

    companion object {
        private val BOUNDS_1 = LatLngBounds(LatLng(37.4282975, 126.7644840), LatLng(37.7014553, 127.1837949))
        private val BOUNDS_2 = LatLngBounds(LatLng(34.8357234, 128.7614072), LatLng(35.3890374, 129.3055979))
    }
}
