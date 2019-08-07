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
package com.naver.maps.map.demo.kotlin.camera

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_camera_event.*
import org.jetbrains.anko.toast

class CameraEventActivity : AppCompatActivity(), OnMapReadyCallback {
    private var positionFlag = false
    private var moving = false

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

        fab.setOnClickListener {
            if (moving) {
                naverMap.cancelTransitions()
            } else {
                val coord = if (positionFlag) COORD_2 else COORD_1

                naverMap.moveCamera(CameraUpdate.scrollTo(coord)
                        .animate(CameraAnimation.Fly, 5000)
                        .cancelCallback {
                            moving = false
                            fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                            toast(R.string.camera_update_cancelled)
                        }
                        .finishCallback {
                            moving = false
                            fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                            toast(R.string.camera_update_finished)
                        })

                moving = true
                fab.setImageResource(R.drawable.ic_stop_black_24dp)

                positionFlag = !positionFlag
            }
        }

        naverMap.addOnCameraChangeListener { _, _ ->
            val position = naverMap.cameraPosition
            camera_change.text = getString(R.string.format_camera_position,
                    position.target.latitude, position.target.longitude, position.zoom, position.tilt, position.bearing)
        }

        naverMap.addOnCameraIdleListener {
            val position = naverMap.cameraPosition
            camera_idle.text = getString(R.string.format_camera_position,
                    position.target.latitude, position.target.longitude, position.zoom, position.tilt, position.bearing)
        }
    }

    companion object {
        private val COORD_1 = LatLng(35.1798159, 129.0750222)
        private val COORD_2 = LatLng(37.5666102, 126.9783881)
    }
}
