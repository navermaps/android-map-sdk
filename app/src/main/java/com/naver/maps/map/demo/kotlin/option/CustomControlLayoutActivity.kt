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
package com.naver.maps.map.demo.kotlin.option

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import kotlinx.android.synthetic.main.activity_custom_control_layout.*

class CustomControlLayoutActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom_control_layout)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: run {
                    val logoMargin = resources.getDimensionPixelSize(R.dimen.logo_margin)
                    val options = NaverMapOptions()
                            .camera(CameraPosition(LatLng(37.5116620, 127.0594274), 16.0, 0.0, 90.0))
                            .indoorEnabled(true)
                            .compassEnabled(false)
                            .scaleBarEnabled(false)
                            .zoomControlEnabled(false)
                            .indoorLevelPickerEnabled(false)
                            .logoMargin(logoMargin, logoMargin, logoMargin, logoMargin)
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
        compass.map = naverMap
        zoom.map = naverMap
        indoor_level_picker.map = naverMap

        scale_bar.map = naverMap

        val scaleBarMargin = resources.getDimensionPixelSize(R.dimen.fab_margin)

        fab.setOnClickListener {
            val right = !scale_bar.isRightToLeftEnabled

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

            scale_bar.isRightToLeftEnabled = right
        }
    }
}
