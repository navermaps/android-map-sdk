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
package com.naver.maps.map.demo.java.misc;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;

public class ProjectionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final PointF crosshairPoint = new PointF(Float.NaN, Float.NaN);
    private TextView textView;
    @Nullable
    private NaverMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_projection);

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
        map = naverMap;

        textView = findViewById(R.id.text);

        Marker marker = new Marker();
        marker.setPosition(NaverMap.DEFAULT_CAMERA_POSITION.target);
        marker.setMap(naverMap);

        naverMap.addOnCameraChangeListener((reason, animated) -> {
            LatLng coord = marker.getPosition();
            PointF point = naverMap.getProjection().toScreenLocation(coord);
            marker.setCaptionText(getString(R.string.format_point_coord,
                point.x, point.y, coord.latitude, coord.longitude));

            updateCrosshairCoord();
        });

        View crosshair = findViewById(R.id.crosshair);
        crosshair.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            crosshairPoint.set(crosshair.getX() + crosshair.getWidth() / 2f,
                crosshair.getY() + crosshair.getHeight() / 2f);
            updateCrosshairCoord();
        });
    }

    private void updateCrosshairCoord() {
        if (map == null || Float.isNaN(crosshairPoint.x) || Float.isNaN(crosshairPoint.y)) {
            return;
        }
        LatLng coord = map.getProjection().fromScreenLocation(crosshairPoint);
        textView.setText(getString(R.string.format_point_coord,
            crosshairPoint.x, crosshairPoint.y, coord.latitude, coord.longitude));
    }
}