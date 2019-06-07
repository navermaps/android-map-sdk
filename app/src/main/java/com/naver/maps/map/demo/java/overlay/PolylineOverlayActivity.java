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
package com.naver.maps.map.demo.java.overlay;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.PolylineOverlay;

public class PolylineOverlayActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final List<LatLng> COORDS_1 = Arrays.asList(
        new LatLng(37.57152, 126.97714),
        new LatLng(37.56607, 126.98268),
        new LatLng(37.56445, 126.97707),
        new LatLng(37.55855, 126.97822));

    private static final List<LatLng> COORDS_2 = Arrays.asList(
        new LatLng(37.57152, 126.97714),
        new LatLng(37.5744287, 126.982625));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_fragment);

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
        int width = getResources().getDimensionPixelSize(R.dimen.overlay_line_width);
        int patternInterval = getResources().getDimensionPixelSize(R.dimen.overlay_pattern_interval);

        PolylineOverlay polylineOverlay1 = new PolylineOverlay();
        polylineOverlay1.setWidth(width);
        polylineOverlay1.setCoords(COORDS_1);
        polylineOverlay1.setColor(ResourcesCompat.getColor(getResources(), R.color.primary, getTheme()));
        polylineOverlay1.setMap(naverMap);

        PolylineOverlay polylineOverlay2 = new PolylineOverlay();
        polylineOverlay2.setWidth(width);
        polylineOverlay2.setCoords(COORDS_2);
        polylineOverlay2.setPattern(patternInterval, patternInterval);
        polylineOverlay2.setColor(Color.GRAY);
        polylineOverlay2.setMap(naverMap);
    }
}
