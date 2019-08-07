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
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons

class MarkerActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: run {
                    val options = NaverMapOptions().camera(CameraPosition(
                            NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom, 30.0, 45.0))
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
        Marker().apply {
            position = LatLng(37.5666102, 126.9783881)
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.57000, 126.97618)
            icon = MarkerIcons.BLACK
            angle = 315f
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.57145, 126.98191)
            icon = OverlayImage.fromResource(R.drawable.ic_info_black_24dp)
            width = resources.getDimensionPixelSize(R.dimen.marker_size)
            height = resources.getDimensionPixelSize(R.dimen.marker_size)
            isFlat = true
            angle = 90f
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56768, 126.98602)
            icon = OverlayImage.fromResource(R.drawable.marker_right_bottom)
            anchor = PointF(1f, 1f)
            angle = 90f
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56436, 126.97499)
            icon = MarkerIcons.YELLOW
            captionAlign = Align.Left
            captionText = getString(R.string.marker_caption_1)
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56138, 126.97970)
            icon = MarkerIcons.PINK
            captionTextSize = 14f
            captionText = getString(R.string.marker_caption_2)
            subCaptionTextSize = 10f
            subCaptionColor = Color.GRAY
            subCaptionText = getString(R.string.marker_sub_caption_2)
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56500, 126.9783881)
            icon = MarkerIcons.BLACK
            iconTintColor = Color.RED
            alpha = 0.5f
            map = naverMap
        }
    }
}
