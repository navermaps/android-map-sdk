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
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

public class MarkerCollisionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private boolean forceShowIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marker_collision);

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
        List<Marker> markers = new ArrayList<>();
        LatLngBounds bounds = naverMap.getContentBounds();

        for (int i = 0; i < 50; ++i) {
            Marker marker = new Marker();
            setImportant(marker, i < 10);
            marker.setPosition(new LatLng(
                (bounds.getNorthLatitude() - bounds.getSouthLatitude()) * Math.random() + bounds.getSouthLatitude(),
                (bounds.getEastLongitude() - bounds.getWestLongitude()) * Math.random() + bounds.getWestLongitude()
            ));
            marker.setCaptionText("Marker #" + i);
            marker.setOnClickListener(overlay -> {
                boolean important = (boolean)marker.getTag();
                setImportant(marker, !important);
                return true;
            });
            marker.setMap(naverMap);
            markers.add(marker);
        }

        findViewById(R.id.hide_collided_symbols).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            for (Marker marker : markers) {
                marker.setHideCollidedSymbols(checked);
            }
        });

        findViewById(R.id.hide_collided_markers).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            for (Marker marker : markers) {
                marker.setHideCollidedMarkers(checked);
            }
        });

        findViewById(R.id.hide_collided_captions).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            for (Marker marker : markers) {
                marker.setHideCollidedCaptions(checked);
            }
        });

        findViewById(R.id.force_show).setOnClickListener(v -> {
            Checkable checkable = (Checkable)v;
            boolean checked = !checkable.isChecked();
            checkable.setChecked(checked);
            forceShowIcon = checked;
            for (Marker marker : markers) {
                boolean important = (boolean)marker.getTag();
                marker.setForceShowIcon(important && forceShowIcon);
            }
        });
    }

    private void setImportant(@NonNull Marker marker, boolean important) {
        marker.setIcon(important ? MarkerIcons.GREEN : MarkerIcons.GRAY);
        marker.setZIndex(important ? 1 : 0);
        marker.setTag(important);
        marker.setForceShowIcon(important && forceShowIcon);
    }
}
