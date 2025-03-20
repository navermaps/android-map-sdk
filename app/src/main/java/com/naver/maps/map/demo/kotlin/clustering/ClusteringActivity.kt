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
package com.naver.maps.map.demo.kotlin.clustering

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.clustering.Clusterer
import com.naver.maps.map.clustering.ClusteringKey
import com.naver.maps.map.clustering.DefaultLeafMarkerUpdater
import com.naver.maps.map.clustering.LeafMarkerInfo
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.MapConstants
import com.naver.maps.map.util.MarkerIcons

class ClusteringActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_fragment)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(
                NaverMapOptions().camera(CameraPosition(MapConstants.EXTENT_KOREA.center, 4.0))
            ).also {
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

    private var clusterer: Clusterer<ItemKey>? = null

    override fun onMapReady(naverMap: NaverMap) {
        clusterer = Clusterer.Builder<ItemKey>()
            .leafMarkerUpdater(object : DefaultLeafMarkerUpdater() {
                override fun updateLeafMarker(info: LeafMarkerInfo, marker: Marker) {
                    super.updateLeafMarker(info, marker)
                    marker.icon = ICONS[info.tag as Int]
                    marker.onClickListener = Overlay.OnClickListener {
                        clusterer?.remove(info.key as ItemKey)
                        true
                    }
                }
            })
            .build()
            .apply {
                val keyTagMap = buildMap(5000) {
                    val south = MapConstants.EXTENT_KOREA.southLatitude
                    val west = MapConstants.EXTENT_KOREA.westLongitude
                    val height = MapConstants.EXTENT_KOREA.northLatitude - south
                    val width = MapConstants.EXTENT_KOREA.eastLongitude - west

                    repeat(5000) { i ->
                        put(
                            ItemKey(
                                i,
                                LatLng(height * Math.random() + south, width * Math.random() + west),
                            ),
                            (Math.random() * ICONS.size).toInt(),
                        )
                    }
                }

                addAll(keyTagMap)
                map = naverMap
            }
    }

    private class ItemKey(val id: Int, private val position: LatLng) : ClusteringKey {
        override fun getPosition() = position

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val itemKey = other as ItemKey
            return id == itemKey.id
        }

        override fun hashCode() = id
    }

    companion object {
        private val ICONS = arrayOf(Marker.DEFAULT_ICON, MarkerIcons.BLUE, MarkerIcons.RED, MarkerIcons.YELLOW)
    }
}
