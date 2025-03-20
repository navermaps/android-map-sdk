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

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckedTextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R

class SnapshotActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_snapshot)

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
        val showControls = findViewById<CheckedTextView>(R.id.toggle_show_controls)

        showControls.setOnClickListener {
            showControls.isChecked = !showControls.isChecked
        }

        val snapshot = findViewById<ImageView>(R.id.snapshot)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fun takeSnapshot() {
            naverMap.takeSnapshot(showControls.isChecked) {
                fab.setImageResource(R.drawable.ic_photo_camera_black_24dp)
                snapshot.setImageBitmap(it)
            }
        }

        fab.setOnClickListener {
            fab.setImageDrawable(CircularProgressDrawable(this).apply {
                setStyle(CircularProgressDrawable.LARGE)
                setColorSchemeColors(Color.WHITE)
                start()
            })

            if (naverMap.isFullyRendered && naverMap.isRenderingStable) {
                takeSnapshot()
            } else {
                naverMap.addOnMapRenderedListener(object : NaverMap.OnMapRenderedListener {
                    override fun onMapRendered(fully: Boolean, stable: Boolean) {
                        if (fully && stable) {
                            takeSnapshot()
                            naverMap.removeOnMapRenderedListener(this)
                        }
                    }
                })
            }
        }
    }
}
