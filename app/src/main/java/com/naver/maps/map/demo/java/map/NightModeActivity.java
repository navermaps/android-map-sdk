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

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Checkable;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

public class NightModeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final LatLng[] MARKER_COORDS = {
        new LatLng(37.5666102, 126.9783881),
        new LatLng(37.57000, 126.97618),
        new LatLng(37.56138, 126.97970)
    };

    private boolean nightModeEnabled = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_night_mode);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions()
                .nightModeEnabled(true)
                .backgroundResource(NaverMap.DEFAULT_BACKGROUND_DRWABLE_DARK)
                .mapType(NaverMap.MapType.Navi)
                .minZoom(4));
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
        List<Marker> markers = new ArrayList<>();

        for (LatLng coord : MARKER_COORDS) {
            Marker marker = new Marker();
            marker.setPosition(coord);
            marker.setIcon(MarkerIcons.GRAY);
            marker.setMap(naverMap);
            markers.add(marker);
        }

        naverMap.addOnOptionChangeListener(() -> {
            if (nightModeEnabled == naverMap.isNightModeEnabled()) {
                return;
            }

            nightModeEnabled = naverMap.isNightModeEnabled();

            naverMap.setBackgroundColor(nightModeEnabled
                ? NaverMap.DEFAULT_BACKGROUND_COLOR_DARK : NaverMap.DEFAULT_BACKGROUND_COLOR_LIGHT);
            naverMap.setBackgroundResource(nightModeEnabled
                ? NaverMap.DEFAULT_BACKGROUND_DRWABLE_DARK : NaverMap.DEFAULT_BACKGROUND_DRWABLE_LIGHT);

            OverlayImage icon = nightModeEnabled ? MarkerIcons.GRAY : Marker.DEFAULT_ICON;

            for (Marker marker : markers) {
                marker.setIcon(icon);
            }
        });

        findViewById(R.id.toggle_night_mode).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            naverMap.setNightModeEnabled(checked);
        });
    }
}
