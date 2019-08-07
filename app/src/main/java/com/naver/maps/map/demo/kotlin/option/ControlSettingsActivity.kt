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
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Checkable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import kotlinx.android.synthetic.main.activity_control_settings.*

class ControlSettingsActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_control_settings)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: run {
                    val options = NaverMapOptions()
                            .camera(CameraPosition(LatLng(37.5116620, 127.0594274), 16.0, 0.0, 90.0))
                            .indoorEnabled(true)
                            .locationButtonEnabled(true)
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
        toggle_compass.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isCompassEnabled = checked
        }

        toggle_scale_bar.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isScaleBarEnabled = checked
        }

        toggle_zoom_control.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isZoomControlEnabled = checked
        }

        toggle_indoor_level_picker.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isIndoorLevelPickerEnabled = checked
        }

        toggle_location_button.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isLocationButtonEnabled = checked
        }

        toggle_logo_click.setOnClickListener { v ->
            val checkable = v as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isLogoClickEnabled = checked
        }
    }
}
