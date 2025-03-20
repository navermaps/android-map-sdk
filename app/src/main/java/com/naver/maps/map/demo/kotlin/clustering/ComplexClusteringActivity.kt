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

import android.graphics.Color
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
import com.naver.maps.map.clustering.Clusterer.ComplexBuilder
import com.naver.maps.map.clustering.ClusteringKey
import com.naver.maps.map.clustering.DefaultClusterMarkerUpdater
import com.naver.maps.map.clustering.DefaultClusterOnClickListener
import com.naver.maps.map.clustering.DefaultDistanceStrategy
import com.naver.maps.map.clustering.DefaultMarkerManager
import com.naver.maps.map.clustering.DistanceStrategy
import com.naver.maps.map.clustering.Node
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComplexClusteringActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_complex_clustering)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(
                NaverMapOptions().camera(CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, 10.0))
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
        clusterer = ComplexBuilder<ItemKey>()
            .minClusteringZoom(9)
            .maxClusteringZoom(16)
            .maxScreenDistance(200.0)
            .thresholdStrategy { zoom ->
                if (zoom <= 11) {
                    0.0
                } else {
                    70.0
                }
            }
            .distanceStrategy(object : DistanceStrategy {
                private val defaultDistanceStrategy = DefaultDistanceStrategy()

                override fun getDistance(zoom: Int, node1: Node, node2: Node): Double {
                    return if (zoom <= 9) {
                        -1.0
                    } else if ((node1.tag as ItemData).gu == (node2.tag as ItemData).gu) {
                        if (zoom <= 11) {
                            -1.0
                        } else {
                            defaultDistanceStrategy.getDistance(zoom, node1, node2)
                        }
                    } else {
                        10000.0
                    }
                }
            })
            .tagMergeStrategy { cluster ->
                if (cluster.maxZoom <= 9) {
                    null
                } else {
                    ItemData("", (cluster.children.first().tag as ItemData).gu)
                }
            }
            .markerManager(object : DefaultMarkerManager() {
                override fun createMarker() = super.createMarker().apply {
                    subCaptionTextSize = 10f
                    subCaptionColor = Color.WHITE
                    subCaptionHaloColor = Color.TRANSPARENT
                }
            })
            .clusterMarkerUpdater { info, marker ->
                val size = info.size
                marker.icon = when {
                    info.minZoom <= 10 -> MarkerIcons.CLUSTER_HIGH_DENSITY
                    size < 10 -> MarkerIcons.CLUSTER_LOW_DENSITY
                    else -> MarkerIcons.CLUSTER_MEDIUM_DENSITY
                }
                marker.subCaptionText =
                    if (info.minZoom == 10) {
                        (info.tag as ItemData).gu
                    } else {
                        ""
                    }
                marker.anchor = DefaultClusterMarkerUpdater.DEFAULT_CLUSTER_ANCHOR
                marker.captionText = size.toString()
                marker.setCaptionAligns(Align.Center)
                marker.captionColor = Color.WHITE
                marker.captionHaloColor = Color.TRANSPARENT
                marker.onClickListener = DefaultClusterOnClickListener(info)
            }
            .leafMarkerUpdater { info, marker ->
                marker.icon = Marker.DEFAULT_ICON
                marker.anchor = Marker.DEFAULT_ANCHOR
                marker.captionText = (info.tag as ItemData).name
                marker.setCaptionAligns(Align.Bottom)
                marker.captionColor = Color.BLACK
                marker.captionHaloColor = Color.WHITE
                marker.subCaptionText = ""
                marker.onClickListener = null
            }
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            val keyTagMap = buildMap {
                assets.open(CSV_ASSET_PATH).bufferedReader().use { reader ->
                    var i = 0
                    reader.forEachLine { line ->
                        val split = line.split(",")
                        put(
                            ItemKey(i++, LatLng(split[3].toDouble(), split[2].toDouble())),
                            ItemData(split[0], split[1]),
                        )
                    }
                }
            }
            runOnUiThread {
                clusterer?.addAll(keyTagMap)
                clusterer?.setMap(naverMap)
            }
        }
    }

    private class ItemKey(val id: Int, private val latLng: LatLng) : ClusteringKey {
        override fun getPosition() = latLng

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val itemKey = other as ItemKey
            return id == itemKey.id
        }

        override fun hashCode() = id
    }

    private class ItemData(val name: String, val gu: String)

    companion object {
        // 데이터 © 서울특별시 (CC BY)
        // 서울 열린데이터 광장 - 서울시 공중화장실 위치정보
        // http://data.seoul.go.kr/dataList/OA-1370/S/1/datasetView.do
        // 2023.04.19.
        private const val CSV_ASSET_PATH = "seoul_toilet.csv"
    }
}
