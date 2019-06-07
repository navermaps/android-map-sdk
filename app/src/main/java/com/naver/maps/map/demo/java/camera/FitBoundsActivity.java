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
package com.naver.maps.map.demo.java.camera;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.demo.R;
import com.naver.maps.map.overlay.Marker;

public class FitBoundsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final LatLngBounds BOUNDS_1 = new LatLngBounds(
        new LatLng(37.4282975, 126.7644840), new LatLng(37.7014553, 127.1837949));
    private static final LatLngBounds BOUNDS_2 = new LatLngBounds(
        new LatLng(34.8357234, 128.7614072), new LatLng(35.3890374, 129.3055979));

    private boolean boundsFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fab);

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
        Marker ne1 = new Marker();
        ne1.setPosition(BOUNDS_1.getNorthEast());
        ne1.setMap(naverMap);

        Marker sw1 = new Marker();
        sw1.setPosition(BOUNDS_1.getSouthWest());
        sw1.setMap(naverMap);

        Marker ne2 = new Marker();
        ne2.setPosition(BOUNDS_2.getNorthEast());
        ne2.setMap(naverMap);

        Marker sw2 = new Marker();
        sw2.setPosition(BOUNDS_2.getSouthWest());
        sw2.setMap(naverMap);

        int padding = getResources().getDimensionPixelSize(R.dimen.fit_bounds_padding);

        findViewById(R.id.fab).setOnClickListener(v -> {
            LatLngBounds bounds = boundsFlag ? BOUNDS_2 : BOUNDS_1;
            naverMap.moveCamera(CameraUpdate.fitBounds(bounds, padding).animate(CameraAnimation.Fly, 5000));
            boundsFlag = !boundsFlag;
        });
    }
}
