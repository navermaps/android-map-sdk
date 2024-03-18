/*
 * Copyright 2018-2024 NAVER Corp.
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

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R

class CustomLocationTrackingActivity : AppCompatActivity(), OnMapReadyCallback {
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation ?: return
            val coord = LatLng(lastLocation)
            val locationOverlay = map.locationOverlay
            locationOverlay.position = coord
            locationOverlay.bearing = lastLocation.bearing
            map.moveCamera(CameraUpdate.scrollTo(coord))
            if (waiting) {
                waiting = false
                fab?.setImageResource(R.drawable.ic_location_disabled_black_24dp)
                locationOverlay.isVisible = true
            }
        }
    }

    private var trackingEnabled = false
    private var locationEnabled = false
    private var waiting = false
    private var fab: FloatingActionButton? = null
    private lateinit var map: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fab)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

        fab = findViewById(R.id.fab)
        fab?.setImageResource(R.drawable.ic_my_location_black_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.all { it == PermissionChecker.PERMISSION_GRANTED }) {
                tryEnableLocation()
            } else {
                fab?.setImageResource(R.drawable.ic_my_location_black_24dp)
            }
            return
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        if (trackingEnabled) {
            tryEnableLocation()
        }
    }

    override fun onStop() {
        super.onStop()
        disableLocation()
    }

    override fun onMapReady(naverMap: NaverMap) {
        map = naverMap

        fab?.setOnClickListener {
            if (trackingEnabled) {
                disableLocation()
                fab?.setImageResource(R.drawable.ic_my_location_black_24dp)
            } else {
                fab?.setImageDrawable(CircularProgressDrawable(this).apply {
                    setStyle(CircularProgressDrawable.LARGE)
                    setColorSchemeColors(Color.WHITE)
                    start()
                })
                tryEnableLocation()
            }
            trackingEnabled = !trackingEnabled
        }
    }

    private fun tryEnableLocation() {
        if (PERMISSIONS.all { ContextCompat.checkSelfPermission(this, it) == PermissionChecker.PERMISSION_GRANTED }) {
            enableLocation()
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE)
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    private fun enableLocation() {
        locationEnabled = true
        waiting = true
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL.toLong()).build(),
                locationCallback,
                Looper.getMainLooper()
            )
    }

    private fun disableLocation() {
        if (!locationEnabled) {
            return
        }
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback)
        locationEnabled = false
    }

    companion object {
        private const val LOCATION_REQUEST_INTERVAL = 1000
        private const val PERMISSION_REQUEST_CODE = 100
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }
}
