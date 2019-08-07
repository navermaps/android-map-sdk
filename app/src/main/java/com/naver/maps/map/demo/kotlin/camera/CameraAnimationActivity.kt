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
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_fab.*

class CameraAnimationActivity : AppCompatActivity(), OnMapReadyCallback {
    private var positionFlag = false

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
            position = POSITION_1.target
            map = naverMap
        }

        Marker().apply {
            position = POSITION_2.target
            map = naverMap
        }

        fab.setOnClickListener {
            val position = if (positionFlag) POSITION_2 else POSITION_1
            val animation = if (positionFlag) CameraAnimation.Linear else CameraAnimation.Easing
            naverMap.moveCamera(CameraUpdate.toCameraPosition(position).animate(animation, 5000))
            positionFlag = !positionFlag
        }
    }

    companion object {
        private val POSITION_1 = CameraPosition(LatLng(37.5666102, 126.9783881), 6.0)
        private val POSITION_2 = CameraPosition(LatLng(35.1798159, 129.0750222), 8.0)
    }
}
