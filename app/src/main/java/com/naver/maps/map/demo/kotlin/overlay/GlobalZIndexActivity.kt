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
package com.naver.maps.map.demo.kotlin.overlay

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.MarkerIcons

class GlobalZIndexActivity : AppCompatActivity(), OnMapReadyCallback {
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
        PathOverlay().apply {
            coords = PATH_COORDS
            width = resources.getDimensionPixelSize(R.dimen.path_overlay_width)
            color = ResourcesCompat.getColor(resources, R.color.primary, theme)
            outlineWidth = resources.getDimensionPixelSize(R.dimen.path_overlay_outline_width)
            outlineColor = Color.WHITE
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.5701761, 126.9799315)
            captionText = getString(R.string.marker_over_path)
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.5664663, 126.9772952)
            icon = MarkerIcons.BLUE
            captionText = getString(R.string.marker_under_path)
            globalZIndex = PathOverlay.DEFAULT_GLOBAL_Z_INDEX - 1
            map = naverMap
        }
    }

    companion object {
        private val PATH_COORDS = listOf(
                LatLng(37.5631345, 126.9767931),
                LatLng(37.5635163, 126.9769240),
                LatLng(37.5635506, 126.9769351),
                LatLng(37.5638061, 126.9770239),
                LatLng(37.5639153, 126.9770605),
                LatLng(37.5639577, 126.9770749),
                LatLng(37.5640074, 126.9770927),
                LatLng(37.5644783, 126.9771755),
                LatLng(37.5649229, 126.9772482),
                LatLng(37.5650330, 126.9772667),
                LatLng(37.5652152, 126.9772971),
                LatLng(37.5654569, 126.9773170),
                LatLng(37.5655173, 126.9773222),
                LatLng(37.5656534, 126.9773258),
                LatLng(37.5660418, 126.9773004),
                LatLng(37.5661985, 126.9772914),
                LatLng(37.5664663, 126.9772952),
                LatLng(37.5668827, 126.9773047),
                LatLng(37.5669467, 126.9773054),
                LatLng(37.5670567, 126.9773080),
                LatLng(37.5671360, 126.9773097),
                LatLng(37.5671910, 126.9773116),
                LatLng(37.5672785, 126.9773122),
                LatLng(37.5674668, 126.9773120),
                LatLng(37.5677264, 126.9773124),
                LatLng(37.5680410, 126.9773068),
                LatLng(37.5689242, 126.9772871),
                LatLng(37.5692829, 126.9772698),
                LatLng(37.5693829, 126.9772669),
                LatLng(37.5696659, 126.9772615),
                LatLng(37.5697524, 126.9772575),
                LatLng(37.5698659, 126.9772499),
                LatLng(37.5699671, 126.9773070),
                LatLng(37.5700151, 126.9773395),
                LatLng(37.5700748, 126.9773866),
                LatLng(37.5701164, 126.9774373),
                LatLng(37.5701903, 126.9776225),
                LatLng(37.5701905, 126.9776723),
                LatLng(37.5701897, 126.9777006),
                LatLng(37.5701869, 126.9784990),
                LatLng(37.5701813, 126.9788591),
                LatLng(37.5701770, 126.9791139),
                LatLng(37.5701741, 126.9792702),
                LatLng(37.5701743, 126.9793098),
                LatLng(37.5701752, 126.9795182),
                LatLng(37.5701761, 126.9799315),
                LatLng(37.5701775, 126.9800380),
                LatLng(37.5701800, 126.9804048),
                LatLng(37.5701832, 126.9809189),
                LatLng(37.5701845, 126.9810197),
                LatLng(37.5701862, 126.9811986),
                LatLng(37.5701882, 126.9814375),
                LatLng(37.5701955, 126.9820897),
                LatLng(37.5701996, 126.9821860))
    }
}