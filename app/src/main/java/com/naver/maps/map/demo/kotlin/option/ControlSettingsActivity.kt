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
import android.view.MenuItem
import android.widget.Checkable
import android.widget.CheckedTextView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.demo.ToolbarActivity

class ControlSettingsActivity : ToolbarActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_control_settings)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(
                NaverMapOptions()
                    .camera(CameraPosition(LatLng(37.5116620, 127.0594274), 16.0, 0.0, 90.0))
                    .indoorEnabled(true)
                    .locationButtonEnabled(true)
            ).also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        findViewById<CheckedTextView>(R.id.toggle_compass).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isCompassEnabled = checked
        }

        findViewById<CheckedTextView>(R.id.toggle_scale_bar).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isScaleBarEnabled = checked
        }

        findViewById<CheckedTextView>(R.id.toggle_zoom_control).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isZoomControlEnabled = checked
        }

        findViewById<CheckedTextView>(R.id.toggle_indoor_level_picker).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isIndoorLevelPickerEnabled = checked
        }

        findViewById<CheckedTextView>(R.id.toggle_location_button).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isLocationButtonEnabled = checked
        }

        findViewById<CheckedTextView>(R.id.toggle_logo_click).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.uiSettings.isLogoClickEnabled = checked
        }
    }
}
