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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Checkable;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;

public class GestureSettingsActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gesture_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
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
        findViewById(R.id.toggle_scroll).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            naverMap.getUiSettings().setScrollGesturesEnabled(checked);
        });

        findViewById(R.id.toggle_zoom).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            naverMap.getUiSettings().setZoomGesturesEnabled(checked);
        });

        findViewById(R.id.toggle_tilt).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            naverMap.getUiSettings().setTiltGesturesEnabled(checked);
        });

        findViewById(R.id.toggle_rotate).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            naverMap.getUiSettings().setRotateGesturesEnabled(checked);
        });

        findViewById(R.id.toggle_stop).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            naverMap.getUiSettings().setStopGesturesEnabled(checked);
        });
    }
}
