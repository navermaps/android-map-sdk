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
package com.naver.maps.map.demo.kotlin.option

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.widget.CompassView
import com.naver.maps.map.widget.IndoorLevelPickerView
import com.naver.maps.map.widget.ScaleBarView
import com.naver.maps.map.widget.ZoomControlView

class CustomControlLayoutActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom_control_layout)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(
                NaverMapOptions()
                    .camera(CameraPosition(LatLng(37.5116620, 127.0594274), 16.0, 0.0, 90.0))
                    .indoorEnabled(true)
                    .compassEnabled(false)
                    .scaleBarEnabled(false)
                    .zoomControlEnabled(false)
                    .indoorLevelPickerEnabled(false)
                    .logoMargin(
                        resources.getDimensionPixelSize(R.dimen.logo_margin),
                        resources.getDimensionPixelSize(R.dimen.logo_margin),
                        resources.getDimensionPixelSize(R.dimen.logo_margin),
                        resources.getDimensionPixelSize(R.dimen.logo_margin)
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
        findViewById<CompassView>(R.id.compass).map = naverMap
        findViewById<ZoomControlView>(R.id.zoom).map = naverMap
        findViewById<IndoorLevelPickerView>(R.id.indoor_level_picker).map = naverMap

        val scaleBar = findViewById<ScaleBarView>(R.id.scale_bar).apply {
            map = naverMap
        }

        val container = findViewById<ConstraintLayout>(R.id.container)
        val scaleBarMargin = resources.getDimensionPixelSize(R.dimen.fab_margin)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val right = !scaleBar.isRightToLeftEnabled

            ConstraintSet().apply {
                clone(container)

                val uiSettings = naverMap.uiSettings
                if (right) {
                    uiSettings.logoGravity = Gravity.START or Gravity.BOTTOM
                    connect(R.id.scale_bar, ConstraintSet.END, R.id.fab, ConstraintSet.START)
                    setMargin(R.id.scale_bar, ConstraintSet.END, scaleBarMargin)
                    clear(R.id.scale_bar, ConstraintSet.START)
                } else {
                    uiSettings.logoGravity = Gravity.START or Gravity.TOP
                    connect(R.id.scale_bar, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    setMargin(R.id.scale_bar, ConstraintSet.START, scaleBarMargin)
                    clear(R.id.scale_bar, ConstraintSet.END)
                }

                applyTo(container)
            }

            scaleBar.isRightToLeftEnabled = right
        }
    }
}
