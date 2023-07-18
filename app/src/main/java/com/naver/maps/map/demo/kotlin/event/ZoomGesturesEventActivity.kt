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
package com.naver.maps.map.demo.kotlin.event

import android.os.Bundle
import android.view.MenuItem
import android.widget.Checkable
import android.widget.CheckedTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R

class ZoomGesturesEventActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_zoom_gestures_event)

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
        var consumeDoubleTap = false
        var consumeTwoFingerTap = false

        naverMap.setOnMapDoubleTapListener { _, coord ->
            Toast.makeText(
                this,
                getString(R.string.format_map_double_tap, coord.latitude, coord.longitude),
                Toast.LENGTH_SHORT
            ).show()
            consumeDoubleTap
        }

        naverMap.setOnMapTwoFingerTapListener { _, coord ->
            Toast.makeText(
                this,
                getString(R.string.format_map_two_finger_tap, coord.latitude, coord.longitude),
                Toast.LENGTH_SHORT
            ).show()
            consumeTwoFingerTap
        }

        findViewById<CheckedTextView>(R.id.toggle_consume_double_tap).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            consumeDoubleTap = checked
        }

        findViewById<CheckedTextView>(R.id.toggle_consume_two_finger_tap).setOnClickListener {
            val checkable = it as Checkable
            val checked = !checkable.isChecked
            checkable.isChecked = checked
            consumeTwoFingerTap = checked
        }
    }
}
