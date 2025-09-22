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

import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.demo.ToolbarActivity

class CustomLocationSourceActivity : ToolbarActivity(), OnMapReadyCallback {
    private class CustomLocationSource : LocationSource, NaverMap.OnMapClickListener {
        private var listener: LocationSource.OnLocationChangedListener? = null

        override fun activate(listener: LocationSource.OnLocationChangedListener) {
            this.listener = listener
        }

        override fun deactivate() {
            listener = null
        }

        override fun onMapClick(point: PointF, coord: LatLng) {
            listener?.onLocationChanged(Location("CustomLocationSource").apply {
                latitude = coord.latitude
                longitude = coord.longitude
                accuracy = 100f
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        val locationSource = CustomLocationSource()
        naverMap.locationSource = locationSource
        naverMap.onMapClickListener = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
        naverMap.addOnLocationChangeListener {
            Toast.makeText(
                this,
                getString(R.string.format_location_changed, it.latitude, it.longitude),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
