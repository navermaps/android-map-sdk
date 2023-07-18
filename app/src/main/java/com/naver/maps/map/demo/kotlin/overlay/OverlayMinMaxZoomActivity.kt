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
package com.naver.maps.map.demo.kotlin.overlay

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
import com.naver.maps.map.util.MarkerIcons

class OverlayMinMaxZoomActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_overlay_min_max_zoom)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(NaverMapOptions().camera(DEFAULT_CAMERA_POSITION)).also {
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
            icon = MarkerIcons.GREEN
            position = LatLng(37.56713851901027, 126.97891430703686)
            captionText = getString(R.string.caption_marker_1)
            minZoom = 15.0
            isMinZoomInclusive = true
            maxZoom = 17.0
            isMaxZoomInclusive = true
            map = naverMap
        }

        Marker().apply {
            icon = MarkerIcons.BLUE
            position = LatLng(37.56713851901027, 126.97786189296272)
            captionText = getString(R.string.caption_marker_2)
            minZoom = 15.0
            isMinZoomInclusive = false
            maxZoom = 17.0
            isMaxZoomInclusive = true
            map = naverMap
        }

        Marker().apply {
            icon = MarkerIcons.RED
            position = LatLng(37.566081877242425, 126.97891430703686)
            captionText = getString(R.string.caption_marker_3)
            minZoom = 15.0
            isMinZoomInclusive = true
            maxZoom = 17.0
            isMaxZoomInclusive = false
            map = naverMap
        }

        Marker().apply {
            icon = MarkerIcons.YELLOW
            position = LatLng(37.566081877242425, 126.97786189296272)
            captionText = getString(R.string.caption_marker_4)
            minZoom = 15.0
            maxZoom = 17.0
            isMinZoomInclusive = false
            isMaxZoomInclusive = false
            map = naverMap
        }

        val zoom = findViewById<TextView>(R.id.zoom)
        naverMap.addOnCameraChangeListener { _, _ ->
            zoom.text = getString(R.string.format_double, naverMap.cameraPosition.zoom)
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            naverMap.moveCamera(CameraUpdate.toCameraPosition(DEFAULT_CAMERA_POSITION).animate(CameraAnimation.Easing))
        }
    }

    companion object {
        private val DEFAULT_CAMERA_POSITION = CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, 16.0)
    }
}
