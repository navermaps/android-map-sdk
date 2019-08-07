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
package com.naver.maps.map.demo.kotlin.location

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_location_tracking.*

class LocationTrackingActivity : AppCompatActivity(), OnMapReadyCallback {
    private var locationSource: FusedLocationSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location_tracking)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: MapFragment.newInstance(NaverMapOptions().locationButtonEnabled(true)).also {
                    supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                }
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            if (item.itemId == android.R.id.home) {
                finish()
                true
            } else {
                super.onOptionsItemSelected(item)
            }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource?.onRequestPermissionsResult(requestCode, permissions, grantResults) == true) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationSource = null
    }

    override fun onMapReady(naverMap: NaverMap) {
        naverMap.locationSource = locationSource

        location_tracking_mode_none.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.None
        }
        location_tracking_mode_no_follow.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
        }
        location_tracking_mode_follow.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }
        location_tracking_mode_face.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.Face
        }

        naverMap.addOnOptionChangeListener {
            val mode = naverMap.locationTrackingMode
            location_tracking_mode_none.isChecked = mode == LocationTrackingMode.None
            location_tracking_mode_no_follow.isChecked = mode == LocationTrackingMode.NoFollow
            location_tracking_mode_follow.isChecked = mode == LocationTrackingMode.Follow
            location_tracking_mode_face.isChecked = mode == LocationTrackingMode.Face

            locationSource?.isCompassEnabled = mode == LocationTrackingMode.Follow || mode == LocationTrackingMode.Face
        }

        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
