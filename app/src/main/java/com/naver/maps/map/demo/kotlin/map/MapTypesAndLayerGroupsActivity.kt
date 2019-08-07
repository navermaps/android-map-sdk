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
package com.naver.maps.map.demo.kotlin.map

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.demo.R
import kotlinx.android.synthetic.main.activity_map_types_and_layer_groups.*

class MapTypesAndLayerGroupsActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_types_and_layer_groups)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
                ?: run {
                    val options = NaverMapOptions()
                            .camera(CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, 16.0, 40.0, 0.0))
                    MapFragment.newInstance(options).also {
                        supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
                    }
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
        map_type.adapter = ArrayAdapter.createFromResource(this, R.array.map_types,
                android.R.layout.simple_spinner_item)
                .apply {
                    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }

        map_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                naverMap.mapType = NaverMap.MapType.valueOf(map_type.adapter.getItem(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val menu = PopupMenu(this, layer_groups).apply {
            inflate(R.menu.layer_groups)
            repeat(menu.size()) { i ->
                val item = menu.getItem(i)
                item.isChecked = naverMap.isLayerGroupEnabled(menuIdToLayerGroup(item.itemId))
            }
            setOnMenuItemClickListener { item ->
                val checked = !item.isChecked
                item.isChecked = checked
                naverMap.setLayerGroupEnabled(menuIdToLayerGroup(item.itemId), checked)
                true
            }
        }

        layer_groups.setOnClickListener {
            menu.show()
        }
    }

    companion object {
        private fun menuIdToLayerGroup(@IdRes id: Int) =
                when (id) {
                    R.id.building -> NaverMap.LAYER_GROUP_BUILDING
                    R.id.traffic -> NaverMap.LAYER_GROUP_TRAFFIC
                    R.id.transit -> NaverMap.LAYER_GROUP_TRANSIT
                    R.id.bicycle -> NaverMap.LAYER_GROUP_BICYCLE
                    R.id.cadastral -> NaverMap.LAYER_GROUP_CADASTRAL
                    R.id.mountain -> NaverMap.LAYER_GROUP_MOUNTAIN
                    else -> throw AssertionError()
                }
    }
}
