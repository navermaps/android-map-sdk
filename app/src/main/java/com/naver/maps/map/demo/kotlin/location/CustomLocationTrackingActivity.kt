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

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import kotlinx.android.synthetic.main.activity_fab.*

class CustomLocationTrackingActivity : AppCompatActivity(), OnMapReadyCallback {
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult == null) {
                return
            }
            map?.let { map ->
                val lastLocation = locationResult.lastLocation
                val coord = LatLng(lastLocation)
                val locationOverlay = map.locationOverlay
                locationOverlay.position = coord
                locationOverlay.bearing = lastLocation.bearing
                map.moveCamera(CameraUpdate.scrollTo(coord))
                if (waiting) {
                    waiting = false
                    fab.setImageResource(R.drawable.ic_location_disabled_black_24dp)
                    locationOverlay.isVisible = true
                }
            }
        }
    }

    private var trackingEnabled: Boolean = false
    private var locationEnabled: Boolean = false
    private var waiting: Boolean = false
    private var map: NaverMap? = null

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

        fab.setImageResource(R.drawable.ic_my_location_black_24dp)
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
                enableLocation()
            } else {
                fab.setImageResource(R.drawable.ic_my_location_black_24dp)
            }
            return
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        if (trackingEnabled) {
            enableLocation()
        }
    }

    override fun onStop() {
        super.onStop()
        disableLocation()
    }

    override fun onMapReady(naverMap: NaverMap) {
        map = naverMap

        fab.setOnClickListener {
            if (trackingEnabled) {
                disableLocation()
                fab.setImageResource(R.drawable.ic_my_location_black_24dp)
            } else {
                fab.setImageDrawable(CircularProgressDrawable(this).apply {
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

    private fun enableLocation() {
        GoogleApiClient.Builder(this)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    @SuppressLint("MissingPermission")
                    override fun onConnected(bundle: Bundle?) {
                        val locationRequest = LocationRequest().apply {
                            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                            interval = LOCATION_REQUEST_INTERVAL.toLong()
                            fastestInterval = LOCATION_REQUEST_INTERVAL.toLong()
                        }

                        LocationServices.getFusedLocationProviderClient(
                                this@CustomLocationTrackingActivity)
                                .requestLocationUpdates(locationRequest, locationCallback, null)
                        locationEnabled = true
                        waiting = true
                    }

                    override fun onConnectionSuspended(i: Int) {
                    }
                })
                .addApi(LocationServices.API)
                .build()
                .connect()
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
                Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}
