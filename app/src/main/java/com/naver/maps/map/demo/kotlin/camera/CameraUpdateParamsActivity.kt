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

import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.CameraUpdateParams
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import kotlinx.android.synthetic.main.activity_fab.*

class CameraUpdateParamsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var step = 0

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
        fab.setOnClickListener {
            val params = CameraUpdateParams()
            when (step) {
                0 -> params.scrollTo(COORD).zoomTo(10.0).tiltTo(0.0)
                1 -> {
                    val deltaX = resources.getDimensionPixelSize(R.dimen.scroll_by_x).toFloat()
                    val deltaY = resources.getDimensionPixelSize(R.dimen.scroll_by_y).toFloat()
                    params.scrollBy(PointF(deltaX, deltaY)).zoomBy(3.0)
                }
                else -> params.rotateBy(90.0).tiltTo(40.0)
            }
            step = (step + 1) % 3
            naverMap.moveCamera(CameraUpdate.withParams(params).animate(CameraAnimation.Easing, 200))
        }
    }

    companion object {
        private val COORD = LatLng(37.5666102, 126.9783881)
    }
}
