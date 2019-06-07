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

import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker

class CustomInfoWindowActivity : AppCompatActivity(), OnMapReadyCallback {
    private class InfoWindowAdapter(private val context: Context) : InfoWindow.ViewAdapter() {
        private var rootView: View? = null
        private var icon: ImageView? = null
        private var text: TextView? = null

        override fun getView(infoWindow: InfoWindow): View {
            val view = rootView ?: View.inflate(context, R.layout.view_custom_info_window, null).also { rootView = it }
            val icon = icon ?: view.findViewById<ImageView>(R.id.icon).also { icon = it }
            val text = text ?: view.findViewById<TextView>(R.id.text).also { text = it }

            val marker = infoWindow.marker
            if (marker != null) {
                icon.setImageResource(R.drawable.ic_place_black_24dp)
                text.text = marker.tag as String?
            } else {
                icon.setImageResource(R.drawable.ic_my_location_black_24dp)
                text.text = context.getString(
                        R.string.format_coord, infoWindow.position.latitude, infoWindow.position.longitude)
            }

            return view
        }
    }

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
        val infoWindow = InfoWindow().apply {
            anchor = PointF(0f, 1f)
            offsetX = resources.getDimensionPixelSize(R.dimen.custom_info_window_offset_x)
            offsetY = resources.getDimensionPixelSize(R.dimen.custom_info_window_offset_y)
            adapter = InfoWindowAdapter(this@CustomInfoWindowActivity)
            setOnClickListener {
                close()
                true
            }
        }

        val marker = Marker().apply {
            position = LatLng(37.57000, 126.97618)
            setOnClickListener {
                infoWindow.open(this)
                true
            }
            tag = "Marker 1"
            map = naverMap
        }

        Marker().apply {
            position = LatLng(37.56138, 126.97970)
            angle = 270f
            setOnClickListener {
                infoWindow.open(this, Align.Left)
                true
            }
            tag = "Marker 2"
            map = naverMap
        }

        infoWindow.open(marker)

        naverMap.setOnMapClickListener { _, coord ->
            infoWindow.position = coord
            infoWindow.open(naverMap)
        }
    }
}
