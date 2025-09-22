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
package com.naver.maps.map.demo.kotlin.location

import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckedTextView
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.demo.ToolbarActivity
import com.naver.maps.map.util.FusedLocationSource

class LocationTrackingActivity : ToolbarActivity(), OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var map: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location_tracking)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(NaverMapOptions().locationButtonEnabled(true)).also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                map.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        map = naverMap

        naverMap.locationSource = locationSource

        val none = findViewById<CheckedTextView>(R.id.location_tracking_mode_none)
        val noFollow = findViewById<CheckedTextView>(R.id.location_tracking_mode_no_follow)
        val follow = findViewById<CheckedTextView>(R.id.location_tracking_mode_follow)
        val face = findViewById<CheckedTextView>(R.id.location_tracking_mode_face)

        none.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.None
        }
        noFollow.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
        }
        follow.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }
        face.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.Face
        }

        naverMap.addOnOptionChangeListener {
            val mode = naverMap.locationTrackingMode
            none.isChecked = mode == LocationTrackingMode.None
            noFollow.isChecked = mode == LocationTrackingMode.NoFollow
            follow.isChecked = mode == LocationTrackingMode.Follow
            face.isChecked = mode == LocationTrackingMode.Face

            locationSource.isCompassEnabled = mode == LocationTrackingMode.Follow || mode == LocationTrackingMode.Face
        }

        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
