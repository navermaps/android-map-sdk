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
package com.naver.maps.map.demo.java.map;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;

public class MapTypesAndLayerGroupsActivity extends AppCompatActivity implements OnMapReadyCallback {
    @NonNull
    private static String menuIdToLayerGroup(@IdRes int id) {
        switch (id) {
        case R.id.building:
            return NaverMap.LAYER_GROUP_BUILDING;
        case R.id.traffic:
            return NaverMap.LAYER_GROUP_TRAFFIC;
        case R.id.transit:
            return NaverMap.LAYER_GROUP_TRANSIT;
        case R.id.bicycle:
            return NaverMap.LAYER_GROUP_BICYCLE;
        case R.id.cadastral:
            return NaverMap.LAYER_GROUP_CADASTRAL;
        case R.id.mountain:
            return NaverMap.LAYER_GROUP_MOUNTAIN;
        default:
            throw new AssertionError();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_types_and_layer_groups);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .camera(new CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, 16, 40, 0)));
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.map_types,
            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.map_type);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence mapType = adapter.getItem(position);
                if (mapType != null) {
                    naverMap.setMapType(NaverMap.MapType.valueOf(mapType.toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        View layerGroupButton = findViewById(R.id.layer_groups);

        PopupMenu menu = new PopupMenu(this, layerGroupButton);
        menu.inflate(R.menu.layer_groups);
        int size = menu.getMenu().size();
        for (int i = 0; i < size; ++i) {
            MenuItem item = menu.getMenu().getItem(i);
            item.setChecked(naverMap.isLayerGroupEnabled(menuIdToLayerGroup(item.getItemId())));
        }
        menu.setOnMenuItemClickListener(item -> {
            boolean checked = !item.isChecked();
            item.setChecked(checked);
            naverMap.setLayerGroupEnabled(menuIdToLayerGroup(item.getItemId()), checked);
            return true;
        });

        layerGroupButton.setOnClickListener(v -> menu.show());
    }
}
