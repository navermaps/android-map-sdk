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
package com.naver.maps.map.demo.java.map;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.demo.ToolbarActivity;

import java.util.Arrays;
import java.util.List;

public class CustomStyleActivity extends ToolbarActivity implements OnMapReadyCallback {
    private static final List<Pair<String, Boolean>> CUSTOM_STYLE_IDS = Arrays.asList(
            new Pair<>(null, false),
            new Pair<>("de2bd5ac-5c2c-490a-874a-11f620bc59ac", false),
            new Pair<>("072a2b46-4cf7-4a60-8a32-c25399b97e4e", true)
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_style);

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            Pair<String, Boolean> customStyle = CUSTOM_STYLE_IDS.get(1);
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                    .customStyleId(customStyle.first)
                    .nightModeEnabled(customStyle.second)
            );
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.custom_styles,
            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.custom_style);
        spinner.setAdapter(adapter);
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, Boolean> customStyle = CUSTOM_STYLE_IDS.get(position);
                naverMap.setCustomStyleId(customStyle.first);
                naverMap.setNightModeEnabled(customStyle.second);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
