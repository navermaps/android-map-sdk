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
package com.naver.maps.map.demo.kotlin.misc

import android.graphics.Color
import android.os.Bundle
import android.support.v4.util.LongSparseArray
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.TileId
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.PolygonOverlay
import com.naver.maps.map.util.TileCoverHelper

class TileCoverHelperActivity : AppCompatActivity(), OnMapReadyCallback {
    private val overlays: LongSparseArray<Overlay> = LongSparseArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

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
        TileCoverHelper().apply {
            setListener { addedTileIds, removedTileIds ->
                addedTileIds.forEach { tileId ->
                    overlays.put(tileId, PolygonOverlay().apply {
                        val bounds = TileId.toLatLngBounds(tileId)

                        coords = listOf(
                                bounds.southWest,
                                bounds.northWest,
                                bounds.northEast,
                                bounds.southEast,
                                bounds.southWest)
                        color = Color.argb(
                                63,
                                (Math.random() * 255 + 0.5).toInt(),
                                (Math.random() * 255 + 0.5).toInt(),
                                (Math.random() * 255 + 0.5).toInt()
                        )
                        map = naverMap
                    })
                }

                removedTileIds.forEach { tileId ->
                    overlays.get(tileId)?.let { overlay ->
                        overlay.map = null
                        overlays.remove(tileId)
                    }
                }
            }

            map = naverMap
        }
    }
}
