/*
 * Copyright 2018-2020 NAVER Corp.
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

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.FusedLocationSource.ActivationHook

class LocationActivationHookActivity : AppCompatActivity(), OnMapReadyCallback {
    class LocationConfirmDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog =
                AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.location_activation_confirm)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            (activity as? LocationActivationHookActivity)?.continueLocationTracking()
                        }
                        .setNegativeButton(R.string.no) { _, _ ->
                            (activity as? LocationActivationHookActivity)?.cancelLocationTracking()
                        }
                        .setOnCancelListener {
                            (activity as? LocationActivationHookActivity)?.cancelLocationTracking()
                        }
                        .create()
    }

    private lateinit var locationSource: FusedLocationSource
    private lateinit var map: NaverMap
    private var locationActivationCallback: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: MapFragment.newInstance(NaverMapOptions().locationButtonEnabled(true)).also {
                    supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                }
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE).apply {
            activationHook = ActivationHook { continueCallback: Runnable ->
                locationActivationCallback = continueCallback
                LocationConfirmDialogFragment().show(supportFragmentManager, null)
            }
        }
    }

    private fun continueLocationTracking() {
        locationActivationCallback?.let {
            it.run()
            locationActivationCallback = null
            locationSource.activationHook = null
        }
    }

    private fun cancelLocationTracking() {
        map.locationTrackingMode = LocationTrackingMode.None
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            if (item.itemId == android.R.id.home) {
                finish()
                true
            } else {
                super.onOptionsItemSelected(item)
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
        naverMap.addOnOptionChangeListener {
            val mode = naverMap.locationTrackingMode
            locationSource.isCompassEnabled = mode == LocationTrackingMode.Follow || mode == LocationTrackingMode.Face
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
