/*
 * Copyright 2018-2024 NAVER Corp.
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
package com.naver.maps.map.demo.java.event;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Checkable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;

public class ZoomGesturesEventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private boolean consumeDoubleTap;
    private boolean consumeTwoFingerTap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zoom_gestures_event);

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
        naverMap.setOnMapDoubleTapListener((point, coord) -> {
            Toast.makeText(this, getString(R.string.format_map_double_tap, coord.latitude, coord.longitude),
                Toast.LENGTH_SHORT).show();
            return consumeDoubleTap;
        });

        naverMap.setOnMapTwoFingerTapListener((point, coord) -> {
            Toast.makeText(this, getString(R.string.format_map_two_finger_tap, coord.latitude, coord.longitude),
                Toast.LENGTH_SHORT).show();
            return consumeTwoFingerTap;
        });

        findViewById(R.id.toggle_consume_double_tap).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            consumeDoubleTap = checked;
        });

        findViewById(R.id.toggle_consume_two_finger_tap).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            consumeTwoFingerTap = checked;
        });
    }
}
