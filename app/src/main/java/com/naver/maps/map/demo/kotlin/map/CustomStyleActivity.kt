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
package com.naver.maps.map.demo.kotlin.map

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import com.naver.maps.map.demo.ToolbarActivity

class CustomStyleActivity : ToolbarActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom_style)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance(NaverMapOptions()
                .customStyleId(CUSTOM_STYLE_IDS[1].first)
                .nightModeEnabled(CUSTOM_STYLE_IDS[1].second)
            ).also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        val spinner = findViewById<Spinner>(R.id.custom_style)

        spinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.custom_styles, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.setSelection(1)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                naverMap.customStyleId = CUSTOM_STYLE_IDS[position].first
                naverMap.isNightModeEnabled = CUSTOM_STYLE_IDS[position].second
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    companion object {
        private val CUSTOM_STYLE_IDS = listOf(
            null to false,
            "de2bd5ac-5c2c-490a-874a-11f620bc59ac" to false,
            "072a2b46-4cf7-4a60-8a32-c25399b97e4e" to true
        )
    }
}
