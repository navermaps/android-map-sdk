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
package com.naver.maps.map.demo.java.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

public class OverlayClickEventActivity extends AppCompatActivity implements OnMapReadyCallback {
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
        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(37.57207, 126.97917));
        marker1.setCaptionText(getString(R.string.consume_event));
        marker1.setOnClickListener(overlay -> {
            if (Marker.DEFAULT_ICON.equals(marker1.getIcon())) {
                marker1.setIcon(MarkerIcons.GRAY);
            } else {
                marker1.setIcon(Marker.DEFAULT_ICON);
            }
            return true;
        });
        marker1.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(37.56361, 126.97439));
        marker2.setCaptionText(getString(R.string.propagate_event));
        marker2.setOnClickListener(overlay -> {
            if (Marker.DEFAULT_ICON.equals(marker2.getIcon())) {
                marker2.setIcon(MarkerIcons.GRAY);
            } else {
                marker2.setIcon(Marker.DEFAULT_ICON);
            }
            return false;
        });
        marker2.setMap(naverMap);

        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(37.56671, 126.98260));
        marker3.setCaptionText(getString(R.string.no_event_listener));
        marker3.setMap(naverMap);

        naverMap.setOnMapClickListener((point, coord) ->
            Toast.makeText(this, getString(R.string.format_map_click, coord.latitude, coord.longitude),
                Toast.LENGTH_SHORT).show());
    }
}
