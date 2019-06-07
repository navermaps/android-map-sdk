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
package com.naver.maps.map.demo.java.option;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.IndoorLevelPickerView;
import com.naver.maps.map.widget.ScaleBarView;
import com.naver.maps.map.widget.ZoomControlView;

public class CustomControlLayoutActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_control_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            int logoMargin = getResources().getDimensionPixelSize(R.dimen.logo_margin);

            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(37.5116620, 127.0594274), 16, 0, 90))
                .indoorEnabled(true)
                .compassEnabled(false)
                .scaleBarEnabled(false)
                .zoomControlEnabled(false)
                .indoorLevelPickerEnabled(false)
                .logoMargin(logoMargin, logoMargin, logoMargin, logoMargin));
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
        this.<CompassView>findViewById(R.id.compass).setMap(naverMap);
        this.<ZoomControlView>findViewById(R.id.zoom).setMap(naverMap);
        this.<IndoorLevelPickerView>findViewById(R.id.indoor_level_picker).setMap(naverMap);

        ScaleBarView scaleBar = findViewById(R.id.scale_bar);
        scaleBar.setMap(naverMap);

        ConstraintLayout container = findViewById(R.id.container);
        int scaleBarMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);

        findViewById(R.id.fab).setOnClickListener(v -> {
            boolean right = !scaleBar.isRightToLeftEnabled();

            UiSettings uiSettings = naverMap.getUiSettings();

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(container);

            if (right) {
                uiSettings.setLogoGravity(Gravity.START | Gravity.BOTTOM);
                constraintSet.connect(R.id.scale_bar, ConstraintSet.END, R.id.fab, ConstraintSet.START);
                constraintSet.setMargin(R.id.scale_bar, ConstraintSet.END, scaleBarMargin);
                constraintSet.clear(R.id.scale_bar, ConstraintSet.START);
            } else {
                uiSettings.setLogoGravity(Gravity.START | Gravity.TOP);
                constraintSet.connect(R.id.scale_bar, ConstraintSet.START, ConstraintSet.PARENT_ID,
                    ConstraintSet.START);
                constraintSet.setMargin(R.id.scale_bar, ConstraintSet.START, scaleBarMargin);
                constraintSet.clear(R.id.scale_bar, ConstraintSet.END);
            }

            constraintSet.applyTo(container);

            scaleBar.setRightToLeftEnabled(right);
        });
    }
}
