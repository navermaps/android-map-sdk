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
package com.naver.maps.map.demo.kotlin.misc

import android.graphics.PointF
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker

class ProjectionActivity : AppCompatActivity(), OnMapReadyCallback {
    private val crosshairPoint = PointF(Float.NaN, Float.NaN)
    private lateinit var map: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_projection)

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
        map = naverMap

        val textView = findViewById<TextView>(R.id.text)

        fun updateCrosshairCoord() {
            if (crosshairPoint.x.isNaN() || crosshairPoint.y.isNaN()) {
                return
            }
            val coord = map.projection.fromScreenLocation(crosshairPoint)
            textView.text = getString(
                R.string.format_point_coord,
                crosshairPoint.x,
                crosshairPoint.y,
                coord.latitude,
                coord.longitude
            )
        }

        val marker = Marker().apply {
            position = NaverMap.DEFAULT_CAMERA_POSITION.target
            map = naverMap
        }

        naverMap.addOnCameraChangeListener { _, _ ->
            val coord = marker.position
            val point = naverMap.projection.toScreenLocation(coord)
            marker.captionText = getString(
                R.string.format_point_coord,
                point.x,
                point.y,
                coord.latitude,
                coord.longitude
            )
            updateCrosshairCoord()
        }

        val crosshair = findViewById<ImageView>(R.id.crosshair)
        crosshair.viewTreeObserver.addOnGlobalLayoutListener {
            crosshairPoint.set(crosshair.x + crosshair.width / 2f, crosshair.y + crosshair.height / 2f)
            updateCrosshairCoord()
        }
    }
}
