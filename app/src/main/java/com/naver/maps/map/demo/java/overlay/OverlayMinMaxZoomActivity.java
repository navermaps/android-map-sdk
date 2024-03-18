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
package com.naver.maps.map.demo.java.overlay;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

public class OverlayMinMaxZoomActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final CameraPosition DEFAULT_CAMERA_POSITION =
        new CameraPosition(NaverMap.DEFAULT_CAMERA_POSITION.target, 16);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_overlay_min_max_zoom);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(DEFAULT_CAMERA_POSITION));
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
        Marker marker1 = new Marker();
        marker1.setIcon(MarkerIcons.GREEN);
        marker1.setPosition(new LatLng(37.56713851901027, 126.97891430703686));
        marker1.setCaptionText(getString(R.string.caption_marker_1));
        marker1.setMinZoom(15);
        marker1.setMinZoomInclusive(true);
        marker1.setMaxZoom(17);
        marker1.setMaxZoomInclusive(true);
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setIcon(MarkerIcons.BLUE);
        marker2.setPosition(new LatLng(37.56713851901027, 126.97786189296272));
        marker2.setCaptionText(getString(R.string.caption_marker_2));
        marker2.setMinZoom(15);
        marker2.setMinZoomInclusive(false);
        marker2.setMaxZoom(17);
        marker2.setMaxZoomInclusive(true);
        marker2.setMap(naverMap);

        Marker marker3 = new Marker();
        marker3.setIcon(MarkerIcons.RED);
        marker3.setPosition(new LatLng(37.566081877242425, 126.97891430703686));
        marker3.setCaptionText(getString(R.string.caption_marker_3));
        marker3.setMinZoom(15);
        marker3.setMinZoomInclusive(true);
        marker3.setMaxZoom(17);
        marker3.setMaxZoomInclusive(false);
        marker3.setMap(naverMap);

        Marker marker4 = new Marker();
        marker4.setIcon(MarkerIcons.YELLOW);
        marker4.setPosition(new LatLng(37.566081877242425, 126.97786189296272));
        marker4.setCaptionText(getString(R.string.caption_marker_4));
        marker4.setMinZoom(15);
        marker4.setMaxZoom(17);
        marker4.setMinZoomInclusive(false);
        marker4.setMaxZoomInclusive(false);
        marker4.setMap(naverMap);

        TextView zoom = findViewById(R.id.zoom);
        naverMap.addOnCameraChangeListener((reason, animated) ->
            zoom.setText(getString(R.string.format_double, naverMap.getCameraPosition().zoom)));

        findViewById(R.id.fab).setOnClickListener(v -> naverMap.moveCamera(
            CameraUpdate.toCameraPosition(DEFAULT_CAMERA_POSITION).animate(CameraAnimation.Easing)));
    }
}
