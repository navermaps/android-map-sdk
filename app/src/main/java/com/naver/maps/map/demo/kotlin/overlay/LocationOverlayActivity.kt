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
package com.naver.maps.map.demo.kotlin.overlay

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.LocationOverlay

class LocationOverlayActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location_overlay)

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
        var animator: Animator? = null

        fun animateCircle(locationOverlay: LocationOverlay) {
            animator?.cancel()

            animator = AnimatorSet().apply {
                val radiusAnimator = ObjectAnimator.ofInt(
                    locationOverlay,
                    "circleRadius",
                    0,
                    resources.getDimensionPixelSize(R.dimen.location_overlay_circle_raduis),
                ).apply {
                    repeatCount = 2
                }

                val colorAnimator = ObjectAnimator.ofInt(
                    locationOverlay,
                    "circleColor",
                    Color.argb(127, 148, 186, 250),
                    Color.argb(0, 148, 186, 250),
                ).apply {
                    setEvaluator(ArgbEvaluator())
                    repeatCount = 2
                }

                duration = 1000
                playTogether(radiusAnimator, colorAnimator)
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        locationOverlay.circleRadius = 0
                    }
                })
                start()
            }
        }

        val locationOverlay = naverMap.locationOverlay.apply {
            subIcon = LocationOverlay.DEFAULT_SUB_ICON_ARROW
            circleOutlineWidth = 0
            isVisible = true
            setOnClickListener {
                animateCircle(this)
                true
            }
        }

        naverMap.setOnMapClickListener { _, coord ->
            locationOverlay.position = coord
            animateCircle(locationOverlay)
        }

        val bearingSeekBar = findViewById<SeekBar>(R.id.seek_bar_bearing)
        val bearingValue = findViewById<TextView>(R.id.value_bearing)
        bearingSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                locationOverlay.bearing = progress.toFloat()
                bearingValue.text = getString(R.string.format_bearing, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        animateCircle(locationOverlay)
    }
}
