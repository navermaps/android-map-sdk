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

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
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
            mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom, 30, 45)));
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
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5666102, 126.9783881));
        marker.setMap(naverMap);

        Marker markerWithCustomIcon = new Marker();
        markerWithCustomIcon.setPosition(new LatLng(37.57000, 126.97618));
        markerWithCustomIcon.setIcon(MarkerIcons.BLACK);
        markerWithCustomIcon.setAngle(315);
        markerWithCustomIcon.setMap(naverMap);

        Marker flatMarker = new Marker();
        flatMarker.setPosition(new LatLng(37.57145, 126.98191));
        flatMarker.setIcon(OverlayImage.fromResource(R.drawable.ic_info_black_24dp));
        flatMarker.setWidth(getResources().getDimensionPixelSize(R.dimen.marker_size));
        flatMarker.setHeight(getResources().getDimensionPixelSize(R.dimen.marker_size));
        flatMarker.setFlat(true);
        flatMarker.setAngle(90);
        flatMarker.setMap(naverMap);

        Marker markerWithAnchor = new Marker();
        markerWithAnchor.setPosition(new LatLng(37.56768, 126.98602));
        markerWithAnchor.setIcon(OverlayImage.fromResource(R.drawable.marker_right_bottom));
        markerWithAnchor.setAnchor(new PointF(1, 1));
        markerWithAnchor.setAngle(90);
        markerWithAnchor.setMap(naverMap);

        Marker markerWithCaption = new Marker();
        markerWithCaption.setPosition(new LatLng(37.56436, 126.97499));
        markerWithCaption.setIcon(MarkerIcons.YELLOW);
        markerWithCaption.setCaptionAlign(Align.Left);
        markerWithCaption.setCaptionText(getString(R.string.marker_caption_1));
        markerWithCaption.setMap(naverMap);

        Marker markerWithSubCaption = new Marker();
        markerWithSubCaption.setPosition(new LatLng(37.56138, 126.97970));
        markerWithSubCaption.setIcon(MarkerIcons.PINK);
        markerWithSubCaption.setCaptionTextSize(14);
        markerWithSubCaption.setCaptionText(getString(R.string.marker_caption_2));
        markerWithSubCaption.setSubCaptionTextSize(10);
        markerWithSubCaption.setSubCaptionColor(Color.GRAY);
        markerWithSubCaption.setSubCaptionText(getString(R.string.marker_sub_caption_2));
        markerWithSubCaption.setMap(naverMap);

        Marker tintColorMarker = new Marker();
        tintColorMarker.setPosition(new LatLng(37.56500, 126.9783881));
        tintColorMarker.setIcon(MarkerIcons.BLACK);
        tintColorMarker.setIconTintColor(Color.RED);
        tintColorMarker.setAlpha(0.5f);
        tintColorMarker.setMap(naverMap);
    }
}
