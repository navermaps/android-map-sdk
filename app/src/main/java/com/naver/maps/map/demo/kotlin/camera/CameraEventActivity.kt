/*
 * Copyright 2018-2023 NAVER Corp.
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
package com.naver.maps.map.demo.kotlin.camera

import android.os.Bundle
import android.view.MenuItem
import android.widget.Checkable
import android.widget.CheckedTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker

class CameraEventActivity : AppCompatActivity(), OnMapReadyCallback {
    private var fab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_camera_event)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
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
        Marker().apply {
            position = COORD_1
            map = naverMap
        }

        Marker().apply {
            position = COORD_2
            map = naverMap
        }

        var moving = false
        var cameraChangeCount = 0
        var cameraIdleCount = 0

        fun setIdle(message: Int) {
            moving = false
            fab?.setImageResource(R.drawable.ic_play_arrow_black_24dp)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        fab = findViewById(R.id.fab)
        fab?.setOnClickListener {
            if (moving) {
                naverMap.cancelTransitions()
            } else {
                naverMap.moveCamera(CameraUpdate.scrollTo(COORD_1)
                    .animate(CameraAnimation.Fly, 3000)
                    .cancelCallback {
                        setIdle(R.string.camera_update_cancelled)
                    }
                    .finishCallback {
                        naverMap.moveCamera(CameraUpdate.scrollTo(COORD_2)
                            .animate(CameraAnimation.Fly, 3000)
                            .cancelCallback {
                                setIdle(R.string.camera_update_cancelled)
                            }
                            .finishCallback {
                                setIdle(R.string.camera_update_finished)
                            })
                    })

                moving = true
                fab?.setImageResource(R.drawable.ic_stop_black_24dp)
            }
        }

        val cameraChange = findViewById<TextView>(R.id.camera_change)
        naverMap.addOnCameraChangeListener { _, _ ->
            val position = naverMap.cameraPosition
            cameraChange.text = getString(
                R.string.format_camera_event,
                ++cameraChangeCount,
                position.target.latitude,
                position.target.longitude,
                position.zoom,
                position.tilt,
                position.bearing
            )
        }

        val cameraIdle = findViewById<TextView>(R.id.camera_idle)
        naverMap.addOnCameraIdleListener {
            val position = naverMap.cameraPosition
            cameraIdle.text = getString(
                R.string.format_camera_event,
                ++cameraIdleCount,
                position.target.latitude,
                position.target.longitude,
                position.zoom,
                position.tilt,
                position.bearing
            )
        }

        findViewById<CheckedTextView>(R.id.toggle_camera_idle_pending).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            naverMap.isCameraIdlePending = checked
        }
    }

    companion object {
        private val COORD_1 = LatLng(35.1798159, 129.0750222)
        private val COORD_2 = LatLng(37.5666102, 126.9783881)
    }
}
